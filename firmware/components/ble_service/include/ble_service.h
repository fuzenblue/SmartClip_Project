#ifndef BLE_SERVICE_H
#define BLE_SERVICE_H

#include <stdint.h>
#include <stdbool.h>

/**
 * @brief Initialize BLE GATT Server
 */
void ble_service_init(void);

/**
 * @brief Send trigger event to connected mobile app
 * 
 * @param event_type 1=Flicker, 2=Audio
 * @param severity 0-100
 */
void ble_send_event(uint8_t event_type, uint8_t severity);

/**
 * @brief Check if BLE is connected
 */
bool ble_is_connected(void);

#endif // BLE_SERVICE_H
