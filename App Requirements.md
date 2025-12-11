# **📱 Part 1: Mobile Application Requirements (SW-SPEC)**

App Name: Smart Clip Companion  
Target OS: Android 10+ (API Level 29+)  
Dev Tools: Android Studio (Kotlin/Java \+ C++ NDK)

### **1\. Functional Requirements (ฟังก์ชันที่ต้องมี)**

#### **Module A: User Interface (UI)**

* **SW-UI-01 (Dashboard):** หน้าหลักต้องแสดงสถานะปัจจุบัน  
  * **Current Risk Score:** แสดงเกจวัดความเสี่ยง (0-100%) หรือสี (เขียว/เหลือง/แดง)  
  * **Recent Triggers:** รายการสิ่งกระตุ้นล่าสุดที่ตรวจพบ (เช่น "10:05 \- High Flicker", "10:15 \- VOC Spike")  
  * **Connection Status:** ไอคอนแสดงสถานะ BLE (Connected/Disconnected)  
* **SW-UI-02 (Symptom Logging \- In-App Feedback):**  
  * มีปุ่ม **"I'm feeling unwell"** หรือ **"Log Symptom"** ที่กดได้ง่าย  
  * เมื่อกด จะมี Popup ให้เลือก:  
    * **Phase:** Prodrome (อาการนำ) / Headache (ปวดหัว) / Recovery (หายแล้ว)  
    * **Severity:** Slider 1-10  
* **SW-UI-03 (History/Stats):** กราฟแท่งแสดงจำนวน Trigger ในแต่ละวัน (Day/Week View)

#### **Module B: Logic & Processing**

* **SW-LOGIC-01 (BLE Service):** ต้องทำงานแบบ **Background Service** ได้ (แม้ปิดหน้าจอ แอปต้องยังรับค่าจาก Smart Clip ได้)  
* **SW-LOGIC-02 (Data Aggregation):** เมื่อได้รับ Trigger จาก BLE ให้บันทึกลง Database (Room/SQLite) พร้อม Timestamp  
* **SW-LOGIC-03 (PSP Engine \- MindSpore Lite):**  
  * ทุกๆ X นาที (หรือเมื่อมี Trigger ใหม่) ให้ดึงข้อมูล History \+ User Log ส่งไปให้โมเดล MindSpore Lite (.ms file)  
  * รับค่า Risk Score ที่ทำนายได้ มาอัปเดตหน้า Dashboard

#### **Module C: Notification**

* **SW-NOTI-01 (Risk Alert):** แจ้งเตือนเมื่อ Risk Score \> Threshold (เช่น 80%)  
  * *Message:* "High migraine risk detected (Flicker). Consider taking a break."  
* **SW-NOTI-02 (Feedback Prompt):** หาก Risk สูงต่อเนื่อง แต่ผู้ใช้เงียบ ให้เด้งถาม "Are you feeling okay?" (Smart Prompting)

# 

# **🏗️ Part 2: Android Project Structure (โครงสร้างไฟล์)**

เราจะใช้โครงสร้างมาตรฐาน **MVVM (Model-View-ViewModel)** เพื่อให้โค้ดสะอาดและจัดการง่ายครับ  
Plaintext  
SmartClipApp/  
├── app/  
│   ├── src/  
│   │   ├── main/  
│   │   │   ├── assets/                 \# \[ZONE 1\] เก็บไฟล์โมเดล AI  
│   │   │   │   └── psp\_model.ms        \# ไฟล์โมเดล MindSpore Lite (Mock ไฟล์เปล่าไว้ก่อน)  
│   │   │   │  
│   │   │   ├── cpp/                    \# \[ZONE 2\] ส่วนเชื่อมต่อ MindSpore (Native C++)  
│   │   │   │   ├── CMakeLists.txt  
│   │   │   │   ├── native-lib.cpp      \# JNI Bridge (Java \<-\> C++)  
│   │   │   │   └── ms\_lite\_wrapper.cpp \# โค้ดเรียก MindSpore Library  
│   │   │   │  
│   │   │   ├── java/com/example/smartclip/  
│   │   │   │   ├── data/               \# \[ZONE 3\] จัดการข้อมูล  
│   │   │   │   │   ├── db/             \# Room Database (เก็บ Log)  
│   │   │   │   │   └── model/          \# Data Classes (TriggerEvent, SymptomLog)  
│   │   │   │   │  
│   │   │   │   ├── service/            \# \[ZONE 4\] เบื้องหลัง  
│   │   │   │   │   ├── BleService.kt   \# จัดการ Bluetooth (สแกน/รับค่า)  
│   │   │   │   │   └── MockBleService.kt \# ★ ตัวจำลองข้อมูล (ใช้ตัวนี้ก่อน)  
│   │   │   │   │  
│   │   │   │   ├── ui/                 \# \[ZONE 5\] หน้าจอ  
│   │   │   │   │   ├── dashboard/      \# หน้ากราฟ/เกจวัด  
│   │   │   │   │   ├── log/            \# หน้ากดบันทึกอาการ  
│   │   │   │   │   └── settings/       \# หน้าสแกนหาอุปกรณ์  
│   │   │   │   │  
│   │   │   │   └── ai/                 \# \[ZONE 6\] ตัวคุม AI  
│   │   │   │       └── PSPPredictor.kt \# คลาสเรียก JNI  
│   │   │   │  
│   │   │   └── res/                    \# รูปภาพ, Layout XML  
│   │   │       ├── layout/             \# ไฟล์จัดหน้าจอ  
│   │   │       └── values/             \# สี, ข้อความ  
│   │   │  
│   │   └── AndroidManifest.xml         \# ขอ Permission (Bluetooth, Location)  
│   │  
│   └── build.gradle                    \# ใส่ Library (MindSpore, Room, Chart)

# **🚀 Part 3: Action Plan (เริ่มเขียนยังไงโดยไม่มีของ)**

ให้เริ่มทำ **"Mock BLE Service"** ก่อนครับ นี่คือกุญแจสำคัญ

### **Step 1: สร้าง UI (Dashboard) ให้เสร็จ**

* วาดหน้าจอที่มีเกจวัดความเสี่ยง และปุ่มกด Log  
* สร้าง List ที่โชว์ประวัติ (เช่น "10:00 \- Flicker Detected")

### **Step 2: สร้าง Mock Data Generator**

แทนที่จะรอรับค่าจาก Bluetooth ให้เขียน **Timer** ขึ้นมาตัวนึงในแอปฯ ครับ

* **ไฟล์:** MockBleService.kt

**Logic:**  
Kotlin  
// ทุกๆ 5 วินาที ให้สุ่มสร้างเหตุการณ์ขึ้นมา  
Timer().scheduleAtFixedRate(0, 5000) {  
    val randomEvent \= if (Random.nextBoolean()) {  
        TriggerEvent(type \= "FLICKER", value \= 120)  
    } else {  
        TriggerEvent(type \= "VOC", value \= 450)  
    }  
    // ส่ง event นี้ไปให้หน้า Dashboard แสดงผลทันที  
    sendBroadcast(randomEvent)  
}

* **ผลลัพธ์:** เมื่อรันแอปบน Emulator คุณจะเห็นตัวเลขเด้งขึ้นมาเอง กราฟขยับเอง เหมือนมี Smart Clip เชื่อมต่ออยู่จริง\!

### **Step 3: เตรียม MindSpore Lite JNI**

* สร้างไฟล์ native-lib.cpp รอไว้  
* เขียนฟังก์ชัน loadModel และ predict เปล่าๆ ไว้ก่อน (ให้ return ค่าสุ่มออกมา)  
* เชื่อมต่อให้ Java เรียกฟังก์ชันนี้แล้วไม่ Crash

### **Step 4: Integrations**

* เมื่อ UI พร้อม, Database พร้อม, AI Mock พร้อม \-\> แอปคุณเสร็จไป 80% แล้วครับ  
* **วันของมาถึง:** คุณแค่เปลี่ยนจาก MockBleService เป็น RealBleService ที่เขียนรอไว้ แอปก็จะทำงานกับของจริงได้เลย