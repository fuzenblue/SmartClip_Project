#include "ble_service.h"
#include "esp_bt.h"
#include "esp_bt_main.h"
#include "esp_gap_ble_api.h"
#include "esp_gatt_common_api.h"
#include "esp_gatts_api.h"
#include "esp_log.h"
#include <stdio.h>
#include <string.h>


#define TAG "BLE_SRV"

// UUIDs matching Android App
// Service: 000000FF-0000-1000-8000-00805F9B34FB -> 16-bit: 0x00FF
// Char:    0000FF01-0000-1000-8000-00805F9B34FB -> 16-bit: 0xFF01

#define GATTS_SERVICE_UUID_TEST 0x00FF
#define GATTS_CHAR_UUID_TEST 0xFF01
#define GATTS_NUM_HANDLE_TEST 4

static uint16_t s_char_handle = 0;
static bool s_connected = false;
static uint16_t s_conn_id = 0;
static esp_gatt_if_t s_gatts_if = 0;

static void gatts_profile_a_event_handler(esp_gatts_cb_event_t event,
                                          esp_gatt_if_t gatts_if,
                                          esp_ble_gatts_cb_param_t *param);

static esp_attr_value_t gatts_char1_val = {
    .attr_max_len = 0x40,
    .attr_len = 0x40,
    .attr_value = NULL,
};

// GAP Advertising Data
static esp_ble_adv_data_t adv_data = {
    .set_scan_rsp = false,
    .include_name = true,
    .include_txpower = false,
    .min_interval = 0x0006,
    .max_interval = 0x0010,
    .appearance = 0x00,
    .manufacturer_len = 0,
    .p_manufacturer_data = NULL,
    .service_data_len = 0,
    .p_service_data = NULL,
    .service_uuid_len = 0,
    .p_service_uuid = NULL,
    .flag = (ESP_BLE_ADV_FLAG_GEN_DISC | ESP_BLE_ADV_FLAG_BREDR_NOT_SPT),
};

static esp_ble_adv_params_t adv_params = {
    .adv_int_min = 0x20,
    .adv_int_max = 0x40,
    .adv_type = ADV_TYPE_IND,
    .own_addr_type = BLE_ADDR_TYPE_PUBLIC,
    .channel_map = ADV_CHNL_ALL,
    .adv_filter_policy = ADV_FILTER_ALLOW_SCAN_ANY_CON_ANY,
};

static void gap_event_handler(esp_gap_ble_cb_event_t event,
                              esp_ble_gap_cb_param_t *param) {
  switch (event) {
  case ESP_GAP_BLE_ADV_DATA_SET_COMPLETE_EVT:
    esp_ble_gap_start_advertising(&adv_params);
    break;
  default:
    break;
  }
}

static void gatts_profile_a_event_handler(esp_gatts_cb_event_t event,
                                          esp_gatt_if_t gatts_if,
                                          esp_ble_gatts_cb_param_t *param) {
  switch (event) {
  case ESP_GATTS_REG_EVT:
    s_gatts_if = gatts_if;
    esp_ble_gap_set_device_name("SmartClip_Device");
    esp_ble_gap_config_adv_data(&adv_data);

    // Create Service
    esp_gatt_srvc_id_t service_id;
    service_id.is_primary = true;
    service_id.id.inst_id = 0x00;
    service_id.id.uuid.len = ESP_UUID_LEN_16;
    service_id.id.uuid.uuid.uuid16 = GATTS_SERVICE_UUID_TEST;
    esp_ble_gatts_create_service(gatts_if, &service_id, GATTS_NUM_HANDLE_TEST);
    break;

  case ESP_GATTS_CREATE_EVT:
    // Add Characteristic
    esp_ble_gatts_add_char(
        param->create.service_handle,
        &(esp_bt_uuid_t){.len = ESP_UUID_LEN_16,
                         .uuid = {.uuid16 = GATTS_CHAR_UUID_TEST}},
        ESP_GATT_PERM_READ | ESP_GATT_PERM_WRITE,
        ESP_GATT_CHAR_PROP_BIT_READ | ESP_GATT_CHAR_PROP_BIT_NOTIFY,
        &gatts_char1_val, NULL);
    // Start Service
    esp_ble_gatts_start_service(param->create.service_handle);
    break;

  case ESP_GATTS_ADD_CHAR_EVT:
    s_char_handle = param->add_char.attr_handle;
    // Add Descriptor (CCCD) for notifications if strict
    break;

  case ESP_GATTS_CONNECT_EVT:
    s_conn_id = param->connect.conn_id;
    s_connected = true;
    ESP_LOGI(TAG, "Device Connected");
    break;

  case ESP_GATTS_DISCONNECT_EVT:
    s_connected = false;
    esp_ble_gap_start_advertising(&adv_params); // Restart Adv
    ESP_LOGI(TAG, "Device Disconnected");
    break;

  default:
    break;
  }
}

static void gatts_event_handler(esp_gatts_cb_event_t event,
                                esp_gatt_if_t gatts_if,
                                esp_ble_gatts_cb_param_t *param) {
  if (event == ESP_GATTS_REG_EVT || gatts_if == ESP_GATT_IF_NONE ||
      gatts_if == s_gatts_if) {
    gatts_profile_a_event_handler(event, gatts_if, param);
  }
}

void ble_service_init(void) {
  ESP_ERROR_CHECK(esp_bt_controller_mem_release(ESP_BT_MODE_CLASSIC_BT));

  esp_bt_controller_config_t bt_cfg = BT_CONTROLLER_INIT_CONFIG_DEFAULT();
  esp_bt_controller_init(&bt_cfg);
  esp_bt_controller_enable(ESP_BT_MODE_BLE);
  esp_bluedroid_init();
  esp_bluedroid_enable();

  esp_ble_gatts_register_callback(gatts_event_handler);
  esp_ble_gap_register_callback(gap_event_handler);
  esp_ble_gatts_app_register(0);

  ESP_LOGI(TAG, "BLE Init Complete");
}

void ble_send_event(uint8_t event_type, uint8_t severity) {
  if (s_connected && s_char_handle > 0) {
    uint8_t data[2] = {event_type, severity};
    // Send Notification
    esp_ble_gatts_send_indicate(s_gatts_if, s_conn_id, s_char_handle, 2, data,
                                false);
    ESP_LOGI(TAG, "Sent Notification: Type %d", event_type);
  }
}

bool ble_is_connected(void) { return s_connected; }
