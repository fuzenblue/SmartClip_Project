# Proposal

**หัวข้อ:** การพัฒนาแอปพลิเคชันนวัตกรรม AI โดยใช้เทคโนโลยี MindSpore (Developing AI Innovation Applications Powered by MindSpore)

## **1\. ชื่อโครงการ (Project Title)**

Smart Clip: Hyper-local Migraine Sensitivity Companion  
(ชื่อไทย: สมาร์ทคลิป: อุปกรณ์ AI พกพาอัจฉริยะเพื่อตรวจจับสิ่งกระตุ้นไมเกรนและสร้างโปรไฟล์ความไวส่วนบุคคล)

## **2\. ที่มาและความสำคัญ (Background)**

ไมเกรนเป็นความผิดปกติทางระบบประสาทที่ซับซ้อน ซึ่งไม่ได้ถูกกระตุ้นเพียงแค่ปัจจัยพื้นฐาน แต่สัมพันธ์กับความเปลี่ยนแปลงของสภาพแวดล้อมที่ละเอียดอ่อน   
งานวิจัยพบว่าสมองของผู้ป่วยมีความไวต่อ "การเปลี่ยนแปลง" (Hypersensitivity to Change) เช่น การลดลงของความกดอากาศเพียงเล็กน้อย (6-10 hPa) เสียงรบกวนในย่านความถี่ต่ำ (Low-frequency Noise) และแสงกะพริบจากแหล่งกำเนิดแสงประดิษฐ์   
อย่างไรก็ตาม อุปกรณ์สวมใส่ทั่วไปในท้องตลาดไม่สามารถตรวจจับค่าความละเอียดสูงเหล่านี้ได้ และมักขาดความสามารถในการเรียนรู้รูปแบบความไวที่จำเพาะเจาะจงต่อบุคคล (Lack of Habituation)

## **3\. นิยามปัญหา (Problem Definition)**

* **ปัญหา "สิ่งกระตุ้นที่มองไม่เห็น" (The Invisible Trigger):** ผู้ป่วยไม่ทราบสาเหตุการปวด เพราะสิ่งกระตุ้นเป็นสิ่งที่มนุษย์ไม่รู้สึกตัว เช่น การเปลี่ยนแปลงความดันอากาศอย่างรวดเร็ว (Barometric Fluctuations) หรือแสงกะพริบความถี่สูง (Invisible Flicker)  
* **ปัญหา "ความต่อเนื่องของข้อมูล" (The Data Continuity):** ขาดอุปกรณ์ที่สามารถตรวจวัดระดับ Hyper-local ที่ปกเสื้อได้อย่างต่อเนื่อง และขาดการวิเคราะห์ความสัมพันธ์ร่วม (Synergistic Interaction) ของปัจจัยหลายอย่างพร้อมกัน เช่น ความร้อนสะสมร่วมกับความชื้น  
* **ปัญหา "ความเป็นส่วนตัว" (Privacy Gap):** ผู้ใช้กังวลเรื่องการดักฟังเสียง หากต้องใช้อุปกรณ์ AI วิเคราะห์เสียงสภาพแวดล้อมตลอดเวลา

## **4\. วัตถุประสงค์ (Objectives)**

1. เพื่อพัฒนา **Smart Clip** อุปกรณ์สวมใส่ที่ใช้เทคนิค **Sensor Fusion** บนชิป ESP32-S3 ร่วมกับเซนเซอร์ความไวสูง (BME680, INMP441, Photodiode) เพื่อตรวจวัดสภาพแวดล้อมระยะประชิด  
2. พัฒนาอัลกอริทึมบน **MindSpore Micro** เพื่อวิเคราะห์สัญญาณเชิงลึก เช่น อัตราการเปลี่ยนแปลงความดัน (Pressure Slope), การวิเคราะห์สเปกตรัมเสียง (FFT), และรูปแบบคลื่นแสง  
3. เพื่อสร้างระบบ **Personalized Sensitivity Profiling (PSP)** โดยใช้ **Huawei ModelArts** บน Cloud ในการเรียนรู้ (Retrain) และปรับปรุงโมเดลให้เข้ากับผู้ใช้แต่ละคนแบบอัตโนมัติ (OTA Update)

## 

## **5\. ประโยชน์ที่คาดว่าจะได้รับ (Contribution)**

* **คุณค่าต่อผู้ใช้ (User Value):** เปลี่ยนการจัดการไมเกรนเป็น "การป้องกันเชิงรุก (Proactive)" โดยแจ้งเตือนสิ่งกระตุ้นที่มองไม่เห็น เช่น ความเสี่ยงจากความกดอากาศต่ำ หรือความเครียดจากความร้อน (Heat Stress) ผู้ใช้สามารถรับรู้และหลีกเลี่ยงสิ่งกระตุ้นก่อนที่จะเกิดอาการรุนแรง  
* **คุณค่าทางเทคนิค (Technical Value):** นำเสนอสถาปัตยกรรม **Privacy-Preserving AI** ที่ส่งเพียงค่า Feature Vectors ขึ้น Cloud โดยไม่ส่งไฟล์เสียงดิบ และใช้ Cloud ช่วยประมวลผลโมเดลที่ซับซ้อน  
* **คุณค่าทางสังคม (Social Value):** ยกระดับคุณภาพชีวิตผู้ป่วยไมเกรนผ่านเทคโนโลยี Wellness ที่ไม่รบกวนความเป็นส่วนตัว 

## **6\. นวัตกรรม (Innovation)**

**A. การบูรณาการเซนเซอร์ระยะใกล้เชิงลึก (Advanced Hyper-local Sensing)** ใช้อัลกอริทึมวิเคราะห์ข้อมูลจากเซนเซอร์เดิมในมิติใหม่เพื่อตรวจจับ "สิ่งกระตุ้นที่มองไม่เห็น":

* **Meteorological Analysis (BME680):** ตรวจจับ **อัตราการเปลี่ยนแปลงความกดอากาศ (Pressure Drop Rate)** เพื่อเตือนภาวะ Barometric Trigger และคำนวณ **Heat Stress Index** จากอุณหภูมิและความชื้นที่สัมพันธ์กับการขยายตัวของหลอดเลือด  
* **Chemical Irritant Profiling (VOC Analysis):** ตรวจจับการพุ่งขึ้นฉับพลัน (Spike) ของสารอินทรีย์ระเหยง่าย (VOCs) เช่น ควันบุหรี่หรือกลิ่นน้ำหอมฉุน ซึ่งเป็นตัวกระตุ้นตัวรับ TRPA1 บนเส้นประสาทไตรเจมินัลโดยตรง  
* **Spectral Audio Analysis (INMP441):** ใช้ FFT ตรวจจับ **เสียงความถี่ต่ำ (400Hz Noise)** ที่ผู้ป่วยไมเกรนมักรู้สึกรำคาญเป็นพิเศษ (Phonophobia) มากกว่าเสียงดังทั่วไป  
* **Artificial Light Detection (Photodiode):** วิเคราะห์รูปแบบคลื่นแสง (AC vs DC) เพื่อแยกแยะแสงประดิษฐ์ที่มีความเสี่ยงแสงสีฟ้า (Blue Light) และการกะพริบ (Flicker) ที่กระตุ้นเซลล์ ipRGCs 

**B. ความเป็นส่วนตัวตั้งแต่การออกแบบ (Privacy-by-Design with Edge AI)** ใช้ **MindSpore Micro** บนอุปกรณ์ในการแปลงข้อมูลดิบ (Raw Audio/Signal) ให้เป็น "ค่าทางคณิตศาสตร์" (Feature Vectors) และลบข้อมูลเสียงทิ้งทันทีที่ต้นทาง ทำให้ข้อมูลที่ส่งออกจากตัวอุปกรณ์มีความปลอดภัย 100%

**C. วงรอบการเรียนรู้ส่วนบุคคล (Closed-Loop Personalization)** ระบบไม่ได้ใช้โมเดลเดียวสำหรับทุกคน แต่ใช้ **Huawei Cloud (ModelArts)** ในการรับข้อมูล Feedback ของผู้ใช้ไป Retrain และส่งโมเดลเวอร์ชันอัปเดต (OTA) กลับมายังมือถือ ทำให้ระบบ "ฉลาดขึ้นและรู้จักผู้ใช้ดีขึ้น" ในทุกๆ วัน 

## 

## **7\. สถาปัตยกรรมระบบ (System Architecture)**

*![][image1]*  
**คำอธิบาย:**

**Device Layer : Smart Clip (Edge Sensing & Pre-processing)**

**Hardware Core:** ใช้ไมโครคอนโทรลเลอร์ **ESP32-S3** ที่มีชุดคำสั่ง AI Vector Instructions ช่วยเร่งการประมวลผลทางคณิตศาสตร์

**Advanced Sensor Grid:**

* **BME680:** ตรวจวัดคุณภาพอากาศครบวงจร ได้แก่ ความดันอากาศ (Pressure), สารระเหย (VOCs), อุณหภูมิ และความชื้น  
* **INMP441 (MEMS Mic):** ไมโครโฟนดิจิทัลความไวสูงสำหรับวิเคราะห์สเปกตรัมเสียง  
* **High-Speed Photodiode:** เซนเซอร์แสงตอบสนองไวระดับไมโครวินาที เพื่อจับการกะพริบ (Flicker)  
* **IMU (6-axis):** ตรวจจับการเคลื่อนไหวเพื่อกรอง Noise และวิเคราะห์พฤติกรรม  
  **Software Engine:** **MindSpore Micro**  
* ทำหน้าที่: **Feature Extraction & Event Filtering**  
* เปลี่ยนข้อมูลดิบ (Raw Data) ให้เป็น "ค่าทางคณิตศาสตร์" (Feature Vectors) ทันทีที่ต้นทาง เช่น แปลงเสียงเป็น MFCCs และแปลงแสงเป็นค่าความถี่ FFT ข้อมูลเสียงและภาพดิบจะถูกทำลายทิ้งทันที (Ephemeral Data Processing) เพื่อรับประกันความเป็นส่วนตัว 100%

**Mobile Layer: Smart Companion App (Gateway & User Interaction)**

**Hardware:** Android Smartphone

**Software Engine:** **MindSpore Lite**

**หน้าที่หลัก:**

* **Inference Engine:** รันโมเดล AI (ไฟล์ .ms) เพื่อทำนาย "ระดับความเสี่ยง (Risk Score)" แบบ Real-time โดยรับค่า Feature Vectors มาจาก Smart Clip  
* **Data Labeling Interface:** ทำหน้าที่เป็น Gateway เชื่อมต่อผู้ใช้กับระบบ โดยให้ผู้ใช้ระบุอาการ (Pain Feedback) เพื่อนำไปจับคู่กับข้อมูลสภาพแวดล้อม สร้างเป็น "Labeled Dataset"  
* **Visualization:** แสดง Dashboard เตือนภัยสิ่งกระตุ้น เช่น กราฟแสดงการพุ่งขึ้นของ VOC หรือแจ้งเตือนความกดอากาศต่ำ

**Cloud Layer: Huawei ModelArts (Training & Personalization Center)**

**Platform:** Huawei Cloud (ModelArts)

**หน้าที่หลัก:**

* **Pattern Recognition:** รับข้อมูล Labeled Dataset ของผู้ใช้แต่ละคนมาวิเคราะห์หาความสัมพันธ์ซับซ้อน (Multivariate Correlation) ที่อุปกรณ์ขนาดเล็กคำนวณไม่ไหว เช่น *"อาการปวดของผู้ใช้ A มักเกิดเมื่อความกดอากาศลดลง 5 hPa ร่วมกับมีกลิ่นสารเคมี (VOC Spike)"*  
* **Personalized Retraining:** สร้างโมเดลเวอร์ชันใหม่ที่ปรับจูนน้ำหนัก (Weights) ให้เข้ากับผู้ใช้คนนั้นโดยเฉพาะ  
* **OTA Manager:** ส่งไฟล์โมเดลใหม่ (.ms) กลับไปยังแอปพลิเคชันมือถือโดยอัตโนมัติ


## **8\. กระบวนการไหลของข้อมูล (Data Pipeline)**

![][image2]  
**ขั้นตอน:**  
**Step 1: Advanced Sensing & Signal Processing (On-Device Analysis)**

ESP32-S3 อ่านค่าจากเซนเซอร์และคำนวณตัวแปรเชิงลึก (Derived Metrics) ตามหลักวิทยาศาสตร์:

* **Atmospheric Dynamics (BME680):**  
  * คำนวณ **Pressure Drop Rate (dP / dt)** เพื่อตรวจจับการลดลงของความกดอากาศในช่วง 6-10 hPa   
  * คำนวณ **Heat Stress Index** จากอุณหภูมิและความชื้น   
* **Chemical Profiling (BME680 Gas Sensor):**  
  * ตรวจจับ **VOC Slope** (อัตราการเพิ่มขึ้นของสารระเหย) เพื่อระบุเหตุการณ์ฉับพลัน เช่น ควันบุหรี่หรือน้ำหอม   
* **Spectral Audio Analysis (INMP441):**  
  * ทำ FFT (Fast Fourier Transform) เพื่อสแกนหาพลังงานเสียงในย่าน **400 Hz** และเสียงความถี่ต่ำ (Low-frequency Drone) ที่กระตุ้นไมเกรนได้มากกว่าเสียงดังปกติ   
* **Light Flicker Analysis (Photodiode):**  
  * วิเคราะห์ Waveform แสงเพื่อแยกแยะแสงธรรมชาติ (DC) ออกจากแสงประดิษฐ์ (AC/PWM) ที่เสี่ยงต่อการมีแสงสีฟ้าและ Flicker

**Step 2: Privacy Preservation & Transmission**

* **Vectorization:** MindSpore Micro แปลงค่าทั้งหมดข้างต้นเป็น **Feature Vector** ขนาดเล็ก (Array of floats)  
* **Transmission:** ส่ง Vector ผ่าน **Bluetooth Low Energy (BLE)** ไปยังโทรศัพท์มือถือ *โดยไม่มีการส่งไฟล์เสียงหรือภาพดิบออกไป*

**Step 3: User Interaction & Data Labeling (Mobile App)**

* **Real-time Alert:** แอปฯ นำ Vector เข้าโมเดล (MindSpore Lite) หากผลทำนายมีความเสี่ยงสูง จะแจ้งเตือนผู้ใช้ทันที  
* **Symptom Logging:** เมื่อผู้ใช้รู้สึกมีอาการ (หรือเมื่อแอปฯ ถาม) ผู้ใช้กดบันทึกระดับความปวด (Pain Scale 1-10)  
* **Data Pairing:** แอปฯ จะบันทึก "Vector ช่วงเวลานั้น" คู่กับ "ระดับความปวด" เก็บลง Local Database รอการอัปโหลด

**Step 4: Cloud Intelligence Loop (Huawei ModelArts)**

* **Synchronization:** เมื่อต่อ Wi-Fi แอปฯ อัปโหลดข้อมูล (Anonymous Logs) ขึ้น Huawei Cloud  
* **Retraining:** ModelArts ทำการ Retrain โมเดลใหม่ โดยเรียนรู้จากประวัติ 7-14 วันล่าสุดของผู้ใช้ เพื่อปรับ Threshold ความไวของแต่ละเซนเซอร์ให้ตรงกับอาการจริง  
* **Optimization:** แปลงโมเดลที่เทรนเสร็จแล้วให้เป็นฟอร์แมต `.ms` (MindSpore Lite)

**Step 5: Model Update (Over-The-Air)**

* แอปพลิเคชันดาวน์โหลดโมเดล `.ms` ตัวใหม่มาติดตั้งแทนตัวเก่า  
* **ผลลัพธ์:** ในวันถัดไป อุปกรณ์จะ "รู้จัก" ผู้ใช้ดีขึ้น ลดการแจ้งเตือนพร่ำเพรื่อ (False Alarm) และเตือนได้แม่นยำขึ้นแม้สิ่งกระตุ้นจะเปลี่ยนไป

## 

## **9\. รายละเอียดโมเดล (Model Description)**

**Model 1: Multi-modal Feature Extractor (MindSpore Micro on ESP32-S3)**

* **หน้าที่:** ทำหน้าที่เป็น "ด่านหน้า" ในการแปลงสัญญาณดิบให้เป็นฟีเจอร์ที่มีความหมาย (Meaningful Features) และคัดกรองสัญญาณรบกวน  
* **Input:** ข้อมูลดิบจากเซนเซอร์ (Raw Audio Buffer, Photodiode Waveform, BME680 Sensor Data)  
* **Processing Logic (Hard-coded & TinyML):**  
  * **Audio:** ใช้ FFT แปลงเสียงเป็นโดเมนความถี่ เน้นจับพลังงานที่ย่าน **400 Hz** (Low-frequency Drone) และคำนวณ dB RMS  
  * **Light:** คำนวณค่า **Flicker Index** และ **Frequency** จากรูปคลื่นแสง (100-200Hz)  
  * **Atmosphere:** คำนวณค่าความชัน (Gradient/Slope) ของ **VOC** และ **Pressure** เพื่อจับการเปลี่ยนแปลงเฉียบพลัน  
* **Output:** **Feature Vector** ขนาดเล็ก (เช่น Array 1x16 float) ที่ระบุสถานะสิ่งแวดล้อมโดยไม่ติดข้อมูลระบุตัวตน

**Model 2: Personalized Risk Predictor (MindSpore Lite on Mobile)**

* **หน้าที่:** ทำนาย "โอกาสเกิดอาการไมเกรน (Migraine Probability)" โดยอิงจากข้อมูลสิ่งแวดล้อมและประวัติส่วนตัว  
* **Architecture:** **Gated Recurrent Unit (GRU)** หรือ **Temporal Convolutional Network (TCN)** เนื่องจากเหมาะกับการประมวลผลข้อมูลอนุกรมเวลา (Time-series) และใช้ทรัพยากรน้อยกว่า LSTM  
* **Input:** Sequence ของ Feature Vectors ย้อนหลัง (เช่น 15-30 นาที) \+ ข้อมูล Time of Day \+ User Feedback ล่าสุด  
* **Personalization Mechanism:** โมเดลนี้ไม่ใช่ Static Model แต่เป็น **Dynamic Model** ที่ไฟล์โมเดล (.ms) จะถูกเปลี่ยนใหม่ทุกๆ ช่วงเวลา (เช่น รายสัปดาห์) ผ่านการ Retrain บน Huawei ModelArts เพื่อปรับจูนน้ำหนัก (Weights) ให้เข้ากับความไวของผู้ใช้คนนั้นๆ เช่น หากผู้ใช้ A แพ้กลิ่นแต่น้ำหนัก (Weight) ส่วน VOC จะสูงกว่าส่วนอื่น

## **10\. การทดสอบและสาธิต (Experiments & Demonstration Plan)**

เนื่องจากการทดลองทางคลินิกต้องใช้เวลานาน เราจะใช้วิธี **Engineering Simulation** เพื่อพิสูจน์สมมติฐานทางเทคนิคทั้ง 3 ด้าน:  
**Engineering Simulation**:**Experiment 1: Advanced Trigger Detection Verification (ทดสอบความแม่นยำเซนเซอร์)**

* **วัตถุประสงค์:** พิสูจน์ว่าอัลกอริทึมบน ESP32 สามารถจับ "Invisible Triggers" ได้จริงตามทฤษฎี  
* **วิธีการ (Methodology):**  
  * **Barometric Test:** ใช้การ Inject Data (ป้อนข้อมูลจำลอง) ของค่าความกดอากาศที่ลดลงในอัตรา 6 hPa/hr เข้าสู่บอร์ด เพื่อดูการแจ้งเตือน  
  * **Specific Audio Test:** ใช้ Function Generator สร้างเสียงความถี่ **400 Hz** และเสียง White Noise ทั่วไป เพื่อพิสูจน์ว่าระบบแยกแยะเสียงรบกวนจำเพาะได้  
  * **Flicker Test:** ใช้หลอดไฟ LED ที่ควบคุมด้วย PWM (100Hz) เทียบกับแสงธรรมชาติ เพื่อยืนยันการตรวจจับ Artificial Light  
  * **VOC Test:** ทดสอบปล่อยไอระเหย (แอลกอฮอล์/น้ำหอม) ห่างจากเซนเซอร์ 50 ซม. เพื่อดู Response Time ของกราฟ

**Experiment 2: Privacy Assurance Audit (ทดสอบความเป็นส่วนตัว)**

* **วัตถุประสงค์:** พิสูจน์ว่าไม่มีข้อมูลเสียงหลุดรอดออกไป  
* **วิธีการ:** ใช้เครื่องมือ **Packet Sniffer** (เช่น Wireshark หรือ nRF Connect) ดักจับข้อมูล Bluetooth ที่ส่งออกจาก Smart Clip ในขณะที่มีการพูดคุย  
* **เกณฑ์ผ่าน:** ข้อมูลที่ดักจับได้ต้องเป็นเพียงตัวเลขฐานสิบหก (Hex Vectors) ไม่สามารถ Reconstruct กลับเป็นไฟล์เสียง (.wav) ได้

**Experiment 3: Closed-Loop Personalization Simulation (ทดสอบระบบเรียนรู้)**

* **วัตถุประสงค์:** แสดงให้เห็นว่า Cloud สามารถทำให้โมเดลฉลาดขึ้นได้  
* **วิธีการ:**  
  * **Phase A (Generic Model):** ป้อนข้อมูลจำลอง (Synthetic Data) ของ "แสงกระพริบ" ใส่แอปฯ และจำลอง User Feedback ว่า "ไม่ปวด" (User ไม่แพ้แสง) \-\> สังเกตว่าระบบยังคงเตือน (False Alarm)  
  * **Phase B (Retraining):** นำข้อมูล Log จาก Phase A ไปเทรนบน Cloud จำลองและอัปเดตโมเดลใหม่  
  * **Phase C (Personalized Model):** ป้อนข้อมูลแสงชุดเดิมอีกครั้ง \-\> สังเกตว่าระบบ **"หยุดเตือน"** หรือลดระดับความเสี่ยงลง (เพราะเรียนรู้แล้วว่า User ไม่แพ้แสง) แต่ยังคงเตือนเรื่อง "กลิ่น" ตามปกติ

## 

## **11\. ตัวชี้วัดความสำเร็จ (Key Performance Indicators \- KPIs)**

| หมวดหมู่ | ตัวชี้วัด (Metric) | เป้าหมาย (Target) | เหตุผลสนับสนุน (Rationale) |
| :---- | :---- | :---- | :---- |
| Hardware | Battery Life |  20 ชั่วโมง | ครอบคลุมการใช้งาน 1 วันเต็ม โดยใช้ Motion Gating ลดการทำงานช่วงพัก |
|  | Processing Latency | \< 50 ms | การประมวลผลบน ESP32 ต้องเร็วกว่าการรับรู้ของมนุษย์เพื่อให้เป็นการเตือน Real-time |
| Sensing | Flicker Detection Accuracy | 90% | ความแม่นยำในการแยกแยะแสงกะพริบ 100Hz ออกจากแสงนิ่ง |
|  | Specific Audio (400Hz) Sensitivity | 85% | ความสามารถในการจับเสียงรบกวนความถี่ต่ำท่ามกลางเสียงทั่วไป |
| System | False Alarm Reduction | 20% | เทียบระหว่างโมเดลเริ่มต้น (Generic) กับโมเดลหลังผ่านไป 7 วัน (Personalized) |
|  | Privacy Leakage | 0 Bytes | ปริมาณข้อมูล Raw Audio/Image ที่ถูกส่งออกจากอุปกรณ์ต้องเป็นศูนย์ |
| Cloud | Model Update Cycle | Daily/Weekly | ระบบต้องรองรับการสร้างและส่งโมเดลใหม่ผ่าน OTA ได้สำเร็จ |

# Requirement Specification

# **Software & System Requirement Specification**

**Project Name:** Smart Clip: Hyper-local Migraine Sensitivity Companion (Aligned with Cloud-Edge Synergy & Advanced Sensing)

## **1\. บทนำ (Introduction)**

### **1.1 ขอบเขตของระบบ (Scope)**

ระบบ Smart Clip พัฒนาขึ้นเพื่อตรวจจับสิ่งกระตุ้นไมเกรนระยะใกล้ (Hyper-local Triggers) โดยครอบคลุมปัจจัยด้านสภาพอากาศ (Pressure/Heat), แสง (Flicker), เสียง (Low-freq Noise), และสารเคมี (VOCs) ระบบทำงานแบบ **Distributed AI** ระหว่างอุปกรณ์สวมใส่ (Edge), แอปพลิเคชันมือถือ (Gateway), และ Huawei Cloud (Training Center) เพื่อสร้างโมเดลความไวส่วนบุคคล (Personalized Model) ที่มีการอัปเดตต่อเนื่องผ่าน OTA 

### **1.2 คำจำกัดความ (Definitions)**

* **Feature Vector:** ชุดตัวเลขทางคณิตศาสตร์ที่แปลงมาจากสัญญาณดิบ (เช่น ค่า FFT ของเสียง) ใช้สำหรับการประมวลผล AI โดยไม่ระบุตัวตน   
* **PSP (Personalized Sensitivity Profiling):** ระบบสร้างโปรไฟล์ความไวส่วนบุคคลที่ปรับปรุงความแม่นยำตาม Feedback ของผู้ใช้   
* **Pressure Drop Rate (dP/dt):** อัตราการลดลงของความกดอากาศเทียบกับเวลา ใช้พยากรณ์ความเสี่ยงไมเกรน   
* **Ephemeral Data Processing:** การประมวลผลข้อมูลดิบแล้วลบทิ้งทันทีที่ต้นทาง (Edge) เพื่อรักษาความเป็นส่วนตัว 

## **2\. สถาปัตยกรรมระบบ (System Overview)**

ระบบแบ่งออกเป็น 3 ส่วนหลัก:  
![][image3]

1. **Device Layer (Smart Clip):** ใช้ ESP32-S3 รัน MindSpore Micro ทำหน้าที่ Sensing และ Vectorization  
2. **Mobile Layer (Gateway):** ใช้ Smartphone รัน MindSpore Lite ทำหน้าที่ Inference แจ้งเตือน และรับ Feedback  
3. **Cloud Layer (Huawei ModelArts):** ทำหน้าที่ Retrain Model จากประวัติผู้ใช้และส่ง OTA Update 

## **3\. ข้อกำหนดทางฮาร์ดแวร์ (Hardware Requirements \- HW)**

| Req ID | รายละเอียด (Description) | หมายเหตุ / เหตุผลสนับสนุน |
| :---- | :---- | :---- |
| HW-01 | หน่วยประมวลผลหลัก **ESP32-S3** | มีชุดคำสั่ง AI Vector Instructions ช่วยเร่งการคำนวณ MindSpore Micro |
| HW-02 | เซนเซอร์วัดสภาพอากาศ **BME680**  | เพื่อวัด Pressure, VOCs (Gas), Temp, Humidity ได้ในตัวเดียว |
| HW-03 | ไมโครโฟนต้องเป็นแบบ MEMS Digital (I2S) เช่น **INMP441** | เพื่อความไวสูงในการวิเคราะห์ Audio Spectrum |
| HW-04 | เซนเซอร์แสงต้องเป็น **High-Speed Photodiode** | ต้องตอบสนองระดับไมโครวินาทีเพื่อจับ Flicker ของแสงประดิษฐ์ |
| HW-05 | ต้องมีเซนเซอร์ **IMU 6-axis** | เพื่อกรองสัญญาณรบกวนจากการเคลื่อนไหว (Motion Artifacts) |
| HW-06 | แบตเตอรี่ต้องรองรับการใช้งานต่อเนื่อง  **20** ชั่วโมง | ตามเป้าหมาย KPI |

## **4\. ข้อกำหนดเฟิร์มแวร์และการประมวลผลที่ขอบ (Firmware & Edge Requirements \- FW)**

| Req ID | ฟังก์ชัน (Feature) | รายละเอียดทางเทคนิค |
| :---- | :---- | :---- |
| FW-01 | MindSpore Micro Integration | ต้องติดตั้ง MindSpore Micro Runtime บน ESP32-S3 เพื่อทำ Feature Extraction |
| FW-02 | Atmospheric Dynamics Logic | ต้องคำนวณค่า Pressure Drop Rate ($dP/dt$) เพื่อจับการลดลงในช่วง 6-10 hPa และคำนวณ Heat Stress Index |
| FW-03 | Chemical Profiling Logic | ต้องคำนวณค่า VOC Slope เพื่อตรวจจับการพุ่งขึ้นฉับพลัน (Spike) ของสารเคมี/กลิ่น |
| FW-04 | Spectral Audio Analysis | ต้องทำ FFT เพื่อสแกนหาพลังงานเสียงในย่าน 400 Hz (Low-freq Drone) และคำนวณ dB RMS |
| FW-05 | Flicker Analysis | ต้องวิเคราะห์ Waveform แสงเพื่อแยกแยะสัญญาณ AC (แสงประดิษฐ์/Flicker) ออกจาก DC (แสงธรรมชาติ) |
| FW-06 | Privacy Preservation | ข้อมูลดิบ (Raw Audio/Image) ต้องถูกลบทิ้งทันทีหลังแปลงเป็น Feature Vector (Ephemeral Processing) |
| FW-07 | BLE Transmission | ส่งเฉพาะ Feature Vectors ผ่าน Bluetooth Low Energy ไปยัง Mobile App |

## **5\. ข้อกำหนดซอฟต์แวร์แอปพลิเคชัน (Mobile App Requirements \- SW)**

*เน้นการเป็น Gateway และ User Interaction*

| Req ID | ฟังก์ชัน (Feature) | รายละเอียดทางเทคนิค |
| :---- | :---- | :---- |
| **SW-01** | **Inference Engine** | รันโมเดล **MindSpore Lite (.ms)** เพื่อทำนาย Risk Score จาก Feature Vector ที่ได้รับ |
| **SW-02** | **Real-time Alert** | แจ้งเตือนผู้ใช้ทันทีเมื่อ Risk Score สูงเกิน Threshold (Latency \< 50ms) |
| **SW-03** | **Data Labeling Interface** | มีหน้าจอให้ผู้ใช้บันทึกระดับความปวด (Pain Scale 1-10) เพื่อใช้เป็น Label จับคู่กับข้อมูล |
| **SW-04** | **Data Synchronization** | อัปโหลด Anonymous Logs (Vector \+ Pain Label) ขึ้น Huawei Cloud เมื่อต่อ Wi-Fi |
| **SW-05** | **OTA Model Update** | ระบบต้องรองรับการดาวน์โหลดไฟล์ .ms จาก Cloud มาติดตั้งแทนไฟล์เดิมอัตโนมัติ |
| **SW-06** | **Dashboard Visualization** | แสดงกราฟแนวโน้มของตัวแปรสำคัญ เช่น กราฟ VOC หรือ Pressure Trend  |

## **6\. ข้อกำหนดระบบคลาวด์ (Cloud & AI Requirements \- CL)**

*เน้นวงรอบการเรียนรู้ (Learning Loop)*

| Req ID | ฟังก์ชัน (Feature) | รายละเอียดทางเทคนิค |
| :---- | :---- | :---- |
| **CL-01** | **Pattern Recognition** | ใช้ **Huawei ModelArts** วิเคราะห์ความสัมพันธ์เชิงพหุ (Multivariate Correlation) จาก Log ของผู้ใช้  |
| **CL-02** | **Retraining Engine** | ระบบต้อง Retrain โมเดลโดยอัตโนมัติโดยใช้ข้อมูลย้อนหลัง 7-14 วัน เพื่อปรับจูนน้ำหนัก (Weights)  |
| **CL-03** | **Model Optimization** | แปลงโมเดลที่ Retrain เสร็จแล้วให้เป็นฟอร์แมต **.ms** (MindSpore Lite) เพื่อส่งกลับมือถือ  |
| **CL-04** | **Personalization Logic** | โมเดลใหม่ต้องสามารถลด False Alarm ได้เมื่อเทียบกับ Generic Model เดิม  |

**7\. ข้อกำหนดที่ไม่ใช่ฟังก์ชัน (Non-Functional Requirements \- NFR)**

### **7.1 ประสิทธิภาพ (Performance)**

* **NFR-01 (Battery):** อุปกรณ์ต้องใช้งานได้นาน  20 ชั่วโมง ต่อการชาร์จ 1 ครั้ง   
* **NFR-02 (Latency):** เวลาในการประมวลผลรวม (Edge → Mobile Alert) ต้องน้อยกว่า 50 ms

### **7.2 ความแม่นยำ (Accuracy)**

* **NFR-03 (Flicker):** ความแม่นยำในการตรวจจับแสงกะพริบ (100Hz) ต้อง $\\ge$ 90%   
* **NFR-04 (Audio):** ความแม่นยำในการตรวจจับเสียงความถี่จำเพาะ (400Hz) ต้อง $\\ge$ 85%  
* **NFR-05 (Optimization):** ต้องลดการแจ้งเตือนผิดพลาด (False Alarm) ได้ $\\ge$ 20% หลังใช้งาน 7 วัน

### **7.3 ความเป็นส่วนตัวและความปลอดภัย (Privacy & Security)**

* **NFR-06 (Zero Leakage):** ปริมาณข้อมูลเสียง/ภาพดิบที่ส่งออกจากอุปกรณ์ต้องเป็น 0 Bytes (ตรวจสอบด้วย Packet Sniffer)   
* **NFR-07 (Data Anonymity):** ข้อมูลที่ส่งขึ้น Cloud ต้องเป็น Vector ที่ไม่ระบุตัวตน (Anonymous Logs) 

# Project Timeline

# **Project Timeline: Smart Clip Development (2 Weeks Sprint)**

### **Week 1: The "Brain" & "Gateway" (Software, AI, & Cloud)**

**เป้าหมาย:** ระบบ Cloud Loop ต้องทำงานได้สมบูรณ์ และแอปพลิเคชันต้องรันโมเดลได้ (โดยใช้ข้อมูลจำลอง/Synthetic Data)

#### **Day 1-2: Environment Setup & Cloud Architecture**

* **สิ่งที่ต้องทำ:**  
  * **Dev Tools:** ติดตั้ง VS Code (ESP-IDF), Android Studio, และ Python (MindSpore environment).  
  * **Huawei Cloud Setup:**  
    * สมัคร/Login Huawei Cloud  
    * เปิด Service **ModelArts** และสร้าง OBS Bucket สำหรับเก็บข้อมูล  
  * **Data Preparation (Synthetic):**  
    * เขียน Python Script สร้างไฟล์ CSV จำลองพฤติกรรม: gen\_flicker.py (สร้างคลื่นแสง AC/DC), gen\_pressure.py (สร้างกราฟความกดอากาศลดลงแบบ Slope), gen\_voc.py (สร้างกราฟ Spike ของกลิ่น).  
    * หาไฟล์เสียงตัวอย่าง (Noise 400Hz, Siren, Silence) แปลงเป็น MFCC array.  
* **สิ่งที่ต้องได้ (Deliverables):**  
  * \[x\] Huawei ModelArts Project พร้อมใช้งาน  
  * \[x\] Dataset จำลอง (Mock Data) ครบทุก Scenario (แสง, เสียง, อากาศ)

#### **Day 3-4: AI Model Training & Conversion (MindSpore)**

* **สิ่งที่ต้องทำ:**  
  * **Train Model 1 (Edge \- Feature Extractor):**  
    * ใช้ MindSpore บน PC เทรนโมเดล TinyCNN ด้วยข้อมูลเสียง/แสงจำลอง  
    * **Convert:** แปลงโมเดลเป็น C-Array (.h / .cc) สำหรับ MindSpore Micro (เพื่อรอใส่ ESP32).  
  * **Train Model 2 (Mobile \- Risk Predictor):**  
    * ใช้ ModelArts เทรนโมเดล GRU/TCN ด้วยข้อมูล CSV ที่จำลองความสัมพันธ์ (เช่น ถ้า Pressure Drop \+ VOC Spike \= Risk High).  
    * **Convert:** แปลงโมเดลเป็น .ms (MindSpore Lite) สำหรับ Android.  
* **สิ่งที่ต้องได้ (Deliverables):**  
  * \[x\] ไฟล์ model\_data.h (สำหรับ Firmware)  
  * \[x\] ไฟล์ risk\_predictor.ms (สำหรับ App)

#### **Day 5-7: Mobile App Development & Mock Integration**

* **สิ่งที่ต้องทำ:**  
  * **App Structure:** สร้างโปรเจกต์ Android (Kotlin) วางโครงสร้าง MVVM.  
  * **MindSpore Lite Integration:** เขียน JNI Bridge เชื่อมต่อไฟล์ .ms เข้ากับแอปฯ ให้สามารถ Input ค่าตัวเลข \-\> Output Risk Score ได้.  
  * **Mock BLE Service (สำคัญ):**  
    * เขียน Code ในแอปฯ ให้ "จำลอง" การรับค่าจาก Bluetooth (Mock Data Generator) โดยไม่ต้องต่อบอร์ดจริง  
    * ทดสอบว่าเมื่อ Mock Data เข้ามา \-\> กราฟขยับ \-\> แจ้งเตือนเด้งถูกต้อง.  
  * **Cloud Sync:** เขียนฟังก์ชันอัปโหลด Log ขึ้น Huawei Cloud (OBS) และดาวน์โหลดไฟล์ .ms ใหม่ (จำลอง OTA).  
* **สิ่งที่ต้องได้ (Deliverables):**  
  * \[x\] ไฟล์ APK ที่ติดตั้งบนมือถือได้  
  * \[x\] หน้า Dashboard ขยับได้เอง (ด้วย Mock Data)  
  * \[x\] ระบบ Cloud Loop: อัปโหลดไฟล์ Log ขึ้น Cloud ได้สำเร็จ

---

### **Week 2: The "Body" & "Senses" (Firmware & Hardware)**

**เป้าหมาย:** เปลี่ยนจาก "ข้อมูลจำลอง" เป็น "ข้อมูลจริง" จากเซนเซอร์ และเชื่อมต่อระบบให้ครบวงจร

#### **Day 8-9: Firmware Logic (Advanced Sensing Algorithm)**

* **สิ่งที่ต้องทำ:**  
  * **Setup ESP32 Project:** สร้างโปรเจกต์ใน ESP-IDF  
  * **Implement Algorithms (ยังไม่ต่อเซนเซอร์):**  
    * เขียนฟังก์ชัน calculate\_pressure\_slope()  
    * เขียนฟังก์ชัน perform\_fft\_400hz()  
    * เขียนฟังก์ชัน detect\_voc\_spike()  
  * **Verify Logic:** เอา Mock Data Array (จาก Week 1\) ยัดใส่ฟังก์ชันพวกนี้ แล้วดูว่า Serial Monitor ปริ้นค่า Feature Vector ออกมาถูกต้องไหม  
  * **BLE Service:** เขียน BLE GATT Service รอไว้สำหรับการส่ง Vector.  
* **สิ่งที่ต้องได้ (Deliverables):**  
  * \[x\] Firmware ที่ Compile ผ่าน และ Logic การคำนวณถูกต้อง (ทดสอบด้วยข้อมูลปลอมในโค้ด)

#### **Day 10-11: Hardware Assembly & Driver Integration**

* **สิ่งที่ต้องทำ:**  
  * **Wiring:** ต่อสาย BME680, INMP441, Photodiode, IMU เข้ากับ ESP32-S3 บน Breadboard.  
  * **Real Drivers:**  
    * เขียน Driver อ่านค่า I2C (BME680/IMU) และ I2S (Mic) ของจริง.  
    * แทนที่ Mock Data ในโค้ดด้วย "Real Sensor Data".  
  * **Calibration:**  
    * ทดสอบเป่าลม/ดมกลิ่น/เปิดไฟกะพริบ ดูว่าค่า Sensor เปลี่ยนแปลงตามจริงไหม  
    * ปรับ Threshold (เช่น ค่า FFT สูงแค่ไหนถึงเรียกว่าเจอเสียง 400Hz).  
* **สิ่งที่ต้องได้ (Deliverables):**  
  * \[x\] ฮาร์ดแวร์ต้นแบบ (Prototype) ที่อ่านค่าสิ่งแวดล้อมจริงได้ครบทุกตัว  
  * \[x\] ESP32 ส่งค่า Feature Vector ของจริงออกมาทาง Serial Monitor ได้

#### **Day 12-13: Full System Integration & Cloud Loop Test**

* **สิ่งที่ต้องทำ:**  
  * **BLE Connection:** เชื่อมต่อ Smart Clip (ESP32) เข้ากับ Mobile App (ปิดโหมด Mock Data ในแอปฯ ให้รับค่าจริงจาก BLE).  
  * **End-to-End Test (The Demo Flow):**  
    * **Action:** ปล่อยควัน/เปิดไฟกะพริบใส่ Smart Clip.  
    * **Reaction:** ดูว่าแอปฯ เด้งเตือน Real-time หรือไม่?  
    * **Feedback:** กดปุ่ม "ปวดหัว" ในแอปฯ.  
    * **Loop:** เช็คใน Huawei Cloud ว่ามีไฟล์ Log ใหม่ขึ้นไปหรือไม่?  
  * **Simulation Retraining:** (เนื่องจากเราไม่มีเวลาเทรนจริง 7 วัน) ให้เตรียมไฟล์โมเดล .ms อีกเวอร์ชัน (V2) วางไว้บน Cloud แล้วกดปุ่มในแอปฯ ให้โหลด V2 มาใช้ เพื่อโชว์ฟีเจอร์ OTA.  
* **สิ่งที่ต้องได้ (Deliverables):**  
  * \[x\] ระบบที่ทำงานครบวงจร (Sensor \-\> AI \-\> App \-\> Cloud \-\> App)

#### **Day 14: Final Polish & Documentation**

* **สิ่งที่ต้องทำ:**  
  * **Hardware Packaging:** จัดสายไฟให้เรียบร้อย หรือใส่เคส (ถ้ามี) ให้ดูเป็น Wearable.  
  * **Demo Recording:** อัดคลิปวิดีโอสาธิตฟีเจอร์ไฮไลท์ (Privacy Proof โดยใช้ nRF Connect โชว์ว่าไม่มีเสียงหลุด, การแจ้งเตือน Real-time).  
  * **Slide Deck:** สรุปผลการทดลองกราฟต่างๆ ลงสไลด์.  
* **สิ่งที่ต้องได้ (Deliverables):**  
  * \[x\] คลิปวิดีโอ Demo  
  * \[x\] สไลด์นำเสนอพร้อมผลทดสอบ

# Dataset Structure

### **1\. Audio Dataset (สำหรับฝึก Audio Classifier)**

เป้าหมาย: เทรนโมเดลจิ๋ว (MindSpore Micro) บน ESP32 ให้แยกแยะเสียงรบกวนจำเพาะ (400Hz) และเสียงแวดล้อมได้

การเปลี่ยนแปลง: เพิ่มหมวดหมู่ low\_freq\_drone เพื่อรองรับการตรวจจับเสียงความถี่ต่ำ

**โครงสร้างโฟลเดอร์บนคอมพิวเตอร์:**

Plaintext

mindspore\_studio/  
└── datasets/  
    └── audio/  
        ├── low\_freq\_drone/        \# เสียงรบกวนความถี่ต่ำ 400Hz (Trigger)  
        │   ├── drone\_400hz\_001.wav  
        │   ├── hum\_noise\_002.wav  
        │   └── ...  
        ├── siren/                 \# เสียงไซเรน (Trigger)  
        │   ├── siren\_001.wav  
        │   └── ...  
        ├── traffic/               \# เสียงจราจรทั่วไป (Noise)  
        │   └── traffic\_001.wav  
        └── silence/               \# เสียงเงียบ/Room Tone (Normal)  
            └── room\_001.wav

* **แหล่งข้อมูล:**  
  * ใช้ **ESC-50 Dataset** สำหรับเสียงทั่วไป  
  * **สำคัญ:** ใช้โปรแกรม (เช่น Audacity หรือ Python) สร้างเสียง **Sine Wave ที่ 400Hz** ผสม Noise เข้าไป เพื่อใช้เป็นข้อมูลสำหรับ Class low\_freq\_drone  
* **Input Shape:** MFCC Spectrogram หรือ FFT Spectrum Array

### 

### **2\. Flicker & Light Dataset (สำหรับทดสอบ Logic แสง)**

เป้าหมาย: ทดสอบอัลกอริทึมแยกแยะ Artificial Light (AC/Flicker) ออกจาก Natural Light (DC)

การเปลี่ยนแปลง: ปรับปรุงข้อมูลจำลองให้มีรูปแบบคลื่นชัดเจน (Waveform Analysis)

**โครงสร้างไฟล์ synthetic\_light.csv:**

ข้อมูลโค้ด

timestamp\_ms, adc\_value, label\_type  
0,            2048,      natural\_dc      \# แสงแดด (ค่าจะนิ่งๆ มี Noise นิดหน่อย)  
1,            2050,      natural\_dc  
...  
100,          4095,      artificial\_ac   \# แสงหลอดไฟ (High Peak)  
105,          0,         artificial\_ac   \# แสงหลอดไฟ (Low Peak) \- จำลอง 100Hz PWM  
110,          4095,      artificial\_ac

* **วิธีสร้าง:** เขียน Python สร้าง Sine Wave หรือ Square Wave (PWM) ความถี่ 100Hz สำหรับ Artificial และสร้างเส้นตรง (Constant) สำหรับ Natural

### **3\. Meteorological & Chemical Dataset (สำหรับทดสอบ Logic อากาศ)**

เป้าหมาย: เนื่องจากเราต้องคำนวณ Slope (ความชัน) ของความดันและ VOC เราจึงต้องมีข้อมูลดิบมาทดสอบ Logic นี้

การเปลี่ยนแปลง: เพิ่ม Dataset ใหม่สำหรับ BME680

**โครงสร้างไฟล์ synthetic\_env.csv:**

ข้อมูลโค้ด

timestamp\_sec, pressure\_hpa, voc\_resistance, label\_event  
0,             1013.0,       50000,          normal  
60,            1013.0,       50000,          normal  
...  
300,           1010.0,       50000,          pressure\_drop   \# ลดลง 3 hPa ในเวลาสั้นๆ (Trigger)  
360,           1007.0,       50000,          pressure\_drop  
...  
600,           1013.0,       10000,          voc\_spike       \# ค่า Resistance ลดฮวบ \= Gas เพิ่ม (Trigger)

* **วิธีสร้าง:** เขียน Python สร้างกราฟเส้นตรงที่ค่อยๆ ลดระดับลง (Linear Decay) สำหรับ Pressure Drop และกราฟที่พุ่งขึ้น/ลงสำหรับ VOC

### 

### **4\. PSP Dataset (สำหรับ Cloud Training / Mobile Inference)**

เป้าหมาย: ใช้เทรนโมเดลส่วนบุคคล (Personalized Model) บน Huawei Cloud

การเปลี่ยนแปลง: เปลี่ยนจาก "นับจำนวน" (Count) เป็น "ค่าฟีเจอร์เชิงลึก" (Feature Vectors) เพื่อความแม่นยำ

โครงสร้างไฟล์ user\_behavior\_log.csv:

แต่ละแถวคือ Data Point ที่ส่งมาจาก Smart Clip \+ Feedback จากผู้ใช้

| timestamp | pressure\_slope (dP/dt) | voc\_slope | audio\_400hz\_energy | flicker\_index | heat\_stress\_idx | user\_pain\_level (Target) |
| :---- | :---- | :---- | :---- | :---- | :---- | :---- |
| 08:00 | \-0.1 (ปกติ) | 0.01 (ปกติ) | 10.5 (ต่ำ) | 0.02 (แสงนิ่ง) | 28 | 0 (ไม่ปวด) |
| 09:00 | **\-6.5 (ลดฮวบ)** | 0.05 | 12.0 | 0.05 | 29 | **3 (เริ่มปวด)** |
| 09:15 | \-1.0 | **50.0 (ฉุนมาก)** | 15.0 | 0.10 | 30 | **7 (ปวดมาก)** |
| 09:30 | \-0.5 | 5.0 | **85.0 (เสียงหึ่งๆ)** | **0.8 (ไฟกะพริบ)** | 30 | **9 (ปวดสุดๆ)** |

* **สิ่งที่โมเดลเห็น:** โมเดลบน Cloud จะดูความสัมพันธ์ เช่น *"ถ้า pressure\_slope ลดต่ำกว่า \-5 AND voc\_slope สูง \-\> pain\_level จะสูง"*

### 

### **🛠️ 5\. Mock Data Structure (สำหรับใส่ใน C Code Firmware)**

**เป้าหมาย:** ข้อมูลจำลองเพื่อใส่ใน mock\_data.h ให้ ESP32-S3 ทำงานได้โดยไม่ต้องต่อเซนเซอร์จริงในระยะแรก

**ไฟล์ firmware/main/mock\_data.h:**

C

\#**ifndef** MOCK\_DATA\_H  
\#**define** MOCK\_DATA\_H

// 1\. Mock Audio (เสียง 400Hz ผสม Noise เพื่อทดสอบ FFT)  
const float MOCK\_AUDIO\_400HZ\[\] \= { 0.1, 0.8, 0.1, \-0.8, ... }; // Sine Wave Pattern

// 2\. Mock Light (แสงกะพริบ 100Hz)  
const uint16\_t MOCK\_LIGHT\_FLICKER\[\] \= { 2048, 4095, 2048, 0, ... };

// 3\. Mock Pressure (จำลองความดันลดลงเรื่อยๆ เพื่อเทส Logic Pressure Drop)  
// ค่าสมมติ: นาทีที่ 1=1010, นาทีที่ 2=1008, นาทีที่ 3=1004 (ลดแรง\!)  
const float MOCK\_PRESSURE\_DROP\_SCENARIO\[\] \= { 1010.0, 1009.5, 1008.0, 1004.0, 1000.0 };

// 4\. Mock VOC (จำลองค่า Gas Resistance ลดลง \= มีกลิ่นฉุน)  
const uint32\_t MOCK\_VOC\_SPIKE\_SCENARIO\[\] \= { 50000, 49000, 48000, 15000, 12000 }; 

\#**endif**

### **✅ สรุป Checklist สิ่งที่ต้องเตรียม:**

1. \[ \] **Folder datasets/audio/low\_freq\_drone:** สร้างไฟล์เสียง Sine Wave 400Hz ใส่เข้าไป  
2. \[ \] **Script gen\_env\_data.py:** เขียน Python สร้าง CSV จำลองค่า Pressure Drop และ VOC Spike  
3. \[ \] **Script gen\_psp\_log.py:** เขียน Python สร้าง CSV จำลองพฤติกรรมผู้ใช้ที่มี Column ละเอียดขึ้น (Slope/Energy/Index)  
4. \[ \] **Header File mock\_data.h:** อัปเดตให้มี Array ของ Pressure และ VOC ด้วย

# System Preparation

# **🛠️ Part 1: The Shopping List & Environment**

เตรียมของและโปรแกรมที่ต้องใช้ พร้อมเหตุผลที่อัปเดตใหม่ตามฟีเจอร์เชิงลึก

### **1\. Hardware (ของที่ต้องสั่ง/เตรียม)**

| รายการ | รุ่นที่แนะนำ | เหตุผล (Why?) |
| :---- | :---- | :---- |
| **MCU Board** | **ESP32-S3 DevKitC** | **สำคัญมาก:** รุ่น S3 มีชุดคำสั่ง **AI Vector Instructions** ช่วยเร่งการคำนวณ FFT (สำหรับเสียง 400Hz) และ MindSpore Inference ได้เร็วกว่า ESP32 ธรรมดาถึง 3 เท่า  |
| **Battery** | **LIR2450 (Rechargeable)** | จ่ายกระแส Peak ได้ดีกว่า CR2032 รองรับการประมวลผลต่อเนื่อง 20 ชม. ตามการคำนวณ Math Model |
| **VOC Sensor** | **BME680** (แนะนำสูงสุด) | จำเป็นต้องใช้รุ่นนี้เพราะวัด **Pressure** และ **Gas (VOC)** ได้พร้อมกัน เพื่อใช้คำนวณ **Pressure Drop Rate** และ **VOC Slope** ในตัวเดียว  |
| **Microphone** | **INMP441 (I2S Interface)** | ไมค์ดิจิทัล (I2S) จำเป็นมากสำหรับการทำ **FFT Analysis** เพื่อจับเสียงความถี่ต่ำ (Low-freq Drone) เพราะสัญญาณรบกวนต่ำกว่าไมค์ Analog |
| **Light Sensor** | **Photodiode Module** (Digital/Analog Out) | ต้องเลือกตัวที่ Response time เร็วระดับ $\\mu s$ เพื่อจับ **Waveform** ของแสงกะพริบ 100Hz (Flicker) แยกจากแสงธรรมชาติ |

### **2\. Software Development Environment (สิ่งที่ต้องลงในคอม)**

*แนะนำ: Windows 10/11 หรือ macOS*

| Software | เวอร์ชัน | เหตุผล / ใช้ทำอะไร |
| :---- | :---- | :---- |
| **ESP-IDF** | v5.1.x (Stable) | Framework มาตรฐานของ ESP32 จำเป็นต้องใช้เพื่อจูนการจัดการ Memory สำหรับ MindSpore Micro |
| **Python** | 3.9 | เวอร์ชันเสถียรสำหรับ MindSpore ใช้รัน Script สร้าง **Synthetic Data** (Pressure/Flicker CSV) |
| **MindSpore** | 2.2.0 (CPU) | Framework หลักสำหรับเทรนโมเดลบน PC (Cloud Model Simulation) |
| **MindSpore Lite** | 2.2.0 (Converter) | เครื่องมือแปลงโมเดลจาก PC $\\to$ .ms (มือถือ) และ .cc (ESP32) |
| **Docker** | ล่าสุด | ทางเลือกสำรองถ้าไม่อยากลงโปรแกรมเยอะ ดึง Image ของ Huawei มาใช้ได้เลย |

# **🚀 Part 2: Step-by-Step Implementation (Code First)**

เราจะเริ่มจาก "สมอง" (AI & Data) ก่อน แล้วค่อยไป "ร่างกาย" (Firmware)

### **Step 1: Setup AI Environment & Data Gen**

เราต้องเตรียมข้อมูลจำลอง (Synthetic Data) ตามโครงสร้าง Dataset ใหม่ก่อน

1. **ติดตั้ง Python & Libraries:**

Bash  
pip install numpy matplotlib pandas scikit-learn  
\# ติดตั้ง MindSpore (เช็คลิงก์ล่าสุดที่เว็บ Official)  
pip install https://ms-release.obs.cn-north-4.myhuaweicloud.com/2.2.0/MindSpore/cpu/x86\_64/mindspore-2.2.0-cp39-cp39-win\_amd64.whl

2. **Generate Synthetic Data (สร้างข้อมูลจำลอง):**  
   * เขียน Script gen\_env\_data.py เพื่อสร้างไฟล์ CSV จำลองเหตุการณ์:  
     * **Pressure Drop:** สร้างกราฟเส้นตรงที่ลดลง 6-10 hPa ในช่วงเวลาสั้นๆ  
     * **VOC Spike:** สร้างกราฟที่ค่าพุ่งสูงขึ้นอย่างรวดเร็ว  
     * **Flicker Wave:** สร้าง Sine Wave 100Hz (Artificial) เทียบกับเส้นตรง (Natural)

### **Step 2: Create & Train AI Models (PC Simulation)**

เราต้องทำโมเดล 2 ส่วนตามสถาปัตยกรรมใหม่  
**Task 2.1: Audio Feature Extractor (MindSpore Micro)**

* **เป้าหมาย:** แยกแยะเสียง 400Hz (Drone), Siren, และ Silence  
* **Data:** ใช้ ESC-50 และสร้างไฟล์เสียง Sine Wave 400Hz เพิ่มเข้าไปในโฟลเดอร์ low\_freq\_drone  
* **Code:** เทรน TinyCNN ให้ Output ออกมาเป็น Feature Vector  
* **Export:** แปลงเป็น C-Array (audio\_model\_data.h) เพื่อนำไปฝังใน ESP32

**Task 2.2: Cloud/Mobile Risk Predictor (MindSpore Lite)**

* **เป้าหมาย:** ทำนาย "ความเสี่ยง" จาก Vector รวม (เสียง+แสง+อากาศ)  
* **Data:** ใช้ไฟล์ user\_behavior\_log.csv ที่จำลองขึ้น (Input: Slope/Energy \-\> Output: Pain Level)  
* **Code:** เทรนโมเดล GRU/TCN  
* **Export:** แปลงเป็นไฟล์ .ms สำหรับใส่ในแอป Android

### **Step 3: Firmware Development (Mocking Hardware)**

เขียนโค้ด Firmware โดยใช้ไฟล์ Header mock\_data.h แทนการอ่านค่าจริง

1. **สร้างไฟล์ mock\_data.h:**  
   * เอาข้อมูลจาก CSV ที่ Gen ใน Step 1 มาแปลงเป็น C Array  
   * ใส่ Array ของ MOCK\_PRESSURE\_DROP, MOCK\_VOC\_SPIKE, MOCK\_AUDIO\_400HZ  
2. **เขียน Driver จำลอง (Simulated Sensors):**

C  
// Mock BME680 (Pressure Drop Logic)  
float read\_pressure\_mock() {  
    static int i \= 0;  
    // อ่านค่าจาก Array ที่จำลองการลดระดับลงเรื่อยๆ  
    return MOCK\_PRESSURE\_DROP\_SCENARIO\[i++\];   
}

// Mock Photodiode (Waveform)  
uint16\_t read\_light\_mock() {  
    // อ่านค่า Sine Wave 100Hz เพื่อเทส Logic FFT/Zero-crossing  
    return MOCK\_LIGHT\_FLICKER\[i++\];  
}

3. **Implement Advanced Logic:**  
   * เขียนฟังก์ชันคำนวณ Slope (current\_val \- previous\_val)  
   * เขียนฟังก์ชัน FFT (ใช้ library esp-dsp) เพื่อหาพลังงานที่ 400Hz จาก MOCK\_AUDIO\_400HZ

### **Step 4: Cloud Integration (Huawei ModelArts Loop)**

เตรียมระบบหลังบ้านสำหรับการ Retrain

1. **Huawei Cloud Setup:** สร้าง OBS Bucket เพื่อรอรับไฟล์ Log จากแอป  
2. **Server Simulation:** เขียน Python Script จำลองเป็น Cloud Server

Python  
\# server\_mock.py  
def retrain\_model(user\_logs):  
    print("Retraining model with new slopes and pain levels...")  
    \# จำลองการสร้างไฟล์ .ms เวอร์ชันใหม่  
    return "new\_model\_v2.ms"

# **🔌 Action Plan: สิ่งที่ต้องทำทันทีเมื่อแกะกล่อง**

### **Phase 1: Hardware Assembly (ประกอบร่าง Frankenstein)**

**เป้าหมาย:** ต่อวงจรให้ครบเพื่อรองรับ Advanced Sensing

ผังการต่อสาย (Wiring Diagram): ใช้ Breadboard และสาย Jumper

| เซนเซอร์ | ขาของเซนเซอร์ | ขาของ ESP32-S3 | หมายเหตุ |
| :---- | :---- | :---- | :---- |
| **BME680** | VCC / GND | 3.3V / GND | **ห้ามใช้ 5V** |
|  | SDA | GPIO 4 | I2C Data |
|  | SCL | GPIO 5 | I2C Clock |
| **INMP441** | VCC / GND | 3.3V / GND | \- |
|  | SD | GPIO 1 | I2S Data |
|  | SCK (BCLK) | GPIO 2 | I2S Clock |
|  | WS (LRCK) | GPIO 42 | I2S Word Select |
| **Photodiode** | VCC / GND | 3.3V / GND | \- |
|  | AO (Analog) | GPIO 6 | อ่าน Waveform |
|  | DO (Digital) | GPIO 7 | ใช้เป็น Interrupt (Optional) |

### **Phase 2: Replace Mock with Real Drivers (เขียนโค้ดอ่านค่าจริง)**

เปลี่ยนจากอ่าน Array ใน mock\_data.h เป็นอ่าน Sensor จริง และทดสอบ Logic เชิงลึก

1. **Test Pressure Slope (BME680):**  
   * **Action:** อ่านค่า Pressure ต่อเนื่อง แล้วลองเป่าลมเข้าที่รูเซนเซอร์ (แรงดันจะเปลี่ยน)  
   * **Check:** ดู Serial Monitor ว่าค่า dP/dt เปลี่ยนแปลงไหม  
2. **Test Audio Frequency (INMP441):**  
   * **Action:** เปิดแอพ Tone Generator ในมือถือ สร้างเสียง 400Hz จ่อไมค์  
   * **Check:** ดูว่า FFT Peak ที่ 400Hz พุ่งขึ้นหรือไม่  
3. **Test Light Waveform (Photodiode):**  
   * **Action:** ส่องกับหลอดไฟบ้าน (AC) เทียบกับแสงไฟฉายมือถือ (DC)  
   * **Check:** กราฟที่อ่านได้ต้องต่างกัน (AC เป็นคลื่น, DC เป็นเส้นตรง)

### **Phase 3: Data Collection for AI (เก็บข้อมูลจริง)**

ใช้บอร์ดที่ต่อเสร็จแล้ว เก็บข้อมูลจริงเพื่อ Fine-tune โมเดล

* **Audio:** อัดเสียงสภาพแวดล้อมจริงผ่าน INMP441 แล้วปริ้นค่า FFT ออกมาเก็บเป็น Dataset  
* **Light:** บันทึกค่า ADC จากหลอดไฟหลายๆ ประเภท (LED, Fluorescent) เพื่อดู Pattern ของ Flicker

### **Phase 4: Deploy & Connect (ร่างทอง)**

1. **Deploy MindSpore Micro:** เอาโมเดลที่แปลงเป็น C Array แล้ว ใส่เข้าไปใน Firmware  
2. **BLE Service:** เขียน Code ส่งค่า **Feature Vector** (ไม่ใช่แค่ Trigger Flag) ออกทาง BLE  
3. **App Connect:** ใช้แอป nRF Connect เช็คว่าได้รับค่า Vector เป็น Hex String ถูกต้องหรือไม่

### **ข้อควรระวัง (Safety First)**

* **แรงดันไฟ:** ESP32-S3 และเซนเซอร์ทั้งหมดใช้ **3.3V** เท่านั้น การต่อ 5V จะทำให้ Photodiode หรือ BME680 พังทันที  
* **การเชื่อมต่อ I2S:** สายสัญญาณไมค์ (SCK, WS, SD) ห้ามยาวเกินไป เพราะจะเกิด Noise รบกวนการวิเคราะห์ความถี่  
* **Battery:** อย่าเพิ่งต่อแบต LIR2450 จนกว่าจะเทสระบบผ่าน USB ผ่านหมดแล้ว

# Directory Structure

### **📂 1\. โครงสร้างโฟลเดอร์โปรเจกต์ (Project Directory Structure)**

เพิ่มโฟลเดอร์สำหรับจัดการ Cloud และ Script สร้างข้อมูลจำลองแบบละเอียด  
Plaintext  
SmartClip\_Project/  
│  
├── 🧠 mindspore\_studio/           \# \[ZONE 1\] ส่วนทำ AI บน PC (Python)  
│   ├── datasets/                  \# เก็บไฟล์เสียง .wav, ไฟล์ csv แสง/อากาศ  
│   │   ├── esc50/                 \# Dataset เสียง (Construction, Siren)  
│   │   ├── low\_freq\_drone/        \# เสียงรบกวนความถี่ต่ำ 400Hz  
│   │   └── synthetic\_env/         \# ข้อมูลจำลอง Pressure, VOC, Flicker  
│   ├── models/                    \# ที่เก็บโมเดลที่ Train เสร็จแล้ว  
│   ├── train\_audio.py             \# Script เทรน Audio Model  
│   ├── train\_risk\_pred.py         \# Script เทรนโมเดล Risk Predictor (Cloud/Mobile)  
│   ├── convert\_to\_micro.sh        \# Script แปลงโมเดลเป็นของ ESP32  
│   └── mock\_data\_generator.py     \# ★ ตัวสำคัญ: แปลงไฟล์เสียง/แสง/อากาศ เป็น C Header  
│  
├── ☁️ cloud\_backend/               \# \[ZONE 2\] ส่วนจำลอง Cloud (Optional)  
│   ├── mock\_server.py             \# จำลองรับ Log และส่ง OTA Update  
│   └── obs\_bucket/                \# โฟลเดอร์จำลองเก็บ Log ที่อัปโหลดมา  
│  
└── 🤖 firmware/                   \# \[ZONE 3\] ส่วนโค้ด C/C++ ของ ESP32  
    ├── CMakeLists.txt  
    ├── main/  
    │   ├── main.c                 \# Main Loop  
    │   ├── app\_config.h           \# Config เปิด/ปิด Mock Mode  
    │   └── mock\_data.h            \# ★ ไฟล์ข้อมูลจำลองที่ Gen มาจาก Python  
    │  
    └── components/                \# แยกโมดูลให้ชัดเจน  
        ├── ms\_model/              \# เก็บไฟล์ Model C++ ที่แปลงมาแล้ว  
        ├── sensor\_manager/        \# ตัวจัดการเซนเซอร์ (หัวใจของการ Mock)  
        │   ├── include/  
        │   │   └── sensor\_manager.h  
        │   └── sensor\_manager.c   \# Logic เลือกว่าจะอ่านของจริงหรือของปลอม  
        ├── advanced\_math/         \# \[NEW\] คำนวณ FFT, Slope, Heat Index  
        └── ble\_service/           \# โค้ด Bluetooth

### **🛠️ 2\. ขั้นตอนการทำงาน (Step-by-Step Workflow)**

#### **Step 1: เตรียม AI และข้อมูลจำลอง (ใน mindspore\_studio)**

ก่อนจะเขียน Firmware เราต้องมี "ข้อมูล" และ "โมเดล" ก่อน

1. **หา/สร้าง Dataset:**  
   * **Audio:** โหลด ESC-50 และสร้างไฟล์เสียง Sine Wave 400Hz  
   * **Environment:** เขียน Python สร้าง CSV จำลองค่า Pressure Drop (ลดลง 6-10 hPa) และ VOC Spike  
2. **Train & Export:**  
   * เทรนโมเดล **Audio Feature Extractor** แล้วแปลงเป็น audio\_model\_data.h (สำหรับ ESP32)  
   * เทรนโมเดล **Risk Predictor** แล้วแปลงเป็น .ms (สำหรับ Mobile App)  
3. **สร้าง Mock Data (สำคัญที่สุด):**  
   * เขียน mock\_data\_generator.py ให้อ่านไฟล์ CSV และ WAV แล้วแปลงเป็น C Array

**ตัวอย่าง Python Gen Code:**  
Python  
\# mock\_data\_generator.py  
import numpy as np

\# สมมติอ่านไฟล์เสียง  
audio\_data \= \[0.12, 0.45, \-0.23, ...\]   
\# สมมติสร้างค่า Pressure Drop  
pressure\_data \= \[1013.0, 1012.8, 1008.0, 1005.0\] \# ลดลงแรง\!

with open("../firmware/main/mock\_data.h", "w") as f:  
    f.write("\#ifndef MOCK\_DATA\_H\\n\#define MOCK\_DATA\_H\\n\\n")  
    f.write(f"const float MOCK\_AUDIO\_SAMPLE\[\] \= {{ {', '.join(map(str, audio\_data))} }};\\n")  
    f.write(f"const float MOCK\_PRESSURE\_SAMPLE\[\] \= {{ {', '.join(map(str, pressure\_data))} }};\\n")  
    f.write("\#endif")

#### **Step 2: เขียน Firmware แบบ "Hardware Abstraction" (ใน firmware)**

เขียนโครงสร้างให้สลับโหมดได้ง่ายๆ

1. **สร้างไฟล์ app\_config.h:**

C  
\#**define** USE\_MOCK\_SENSORS  1  // เปลี่ยนเป็น 0 เมื่อฮาร์ดแวร์มาถึง

2. **เขียน sensor\_manager.c (อัปเดตใหม่):**

C  
\#**include** "app\_config.h"  
\#**include** "mock\_data.h"

typedef struct {  
    float\* audio\_buffer;  
    float  pressure\_val;  
    float  voc\_val;  
    uint16\_t light\_val;  
} SensorData\_t;

SensorData\_t read\_sensors() {  
    SensorData\_t data;  
    static int idx \= 0;

    \#**if** USE\_MOCK\_SENSORS \== 1  
        // \--- โหมดจำลอง \---  
        data.audio\_buffer \= \&MOCK\_AUDIO\_SAMPLE\[idx \* 512\]; // เลื่อน Pointer  
        data.pressure\_val \= MOCK\_PRESSURE\_SAMPLE\[idx\];     // อ่านค่าความดันจำลอง  
        data.voc\_val      \= 50000;                         // ค่าสมมติ

        idx++;  
        if (idx \>= 100) idx \= 0; // วนลูป

    \#**else**  
        // \--- โหมดจริง \---  
        // bme680\_get\_sensor\_data(\&bme, \&sensor\_data);  
        // data.pressure\_val \= sensor\_data.pressure;  
    \#**endif**

    return data;  
}

#### **Step 3: เขียน Logic การประมวลผล (Business Logic)**

เขียนอัลกอริทึมวิเคราะห์ข้อมูลเชิงลึก (Advanced Analytics) โดยใช้ข้อมูลปลอมก่อน

1. **Pressure Slope:** เขียนฟังก์ชัน calculate\_slope(current, previous) เพื่อดูว่าค่า MOCK\_PRESSURE\_SAMPLE ลดลงเร็วแค่ไหน  
2. **FFT Analysis:** เขียนฟังก์ชัน FFT เพื่อหาพลังงานที่ 400Hz จาก MOCK\_AUDIO\_SAMPLE  
3. **Inference:** ส่ง Vector ที่ได้เข้า MindSpore Micro เพื่อเช็ค Trigger

#### **Step 4: เชื่อมต่อ Mobile App (Test BLE)**

ทดสอบการส่งผลลัพธ์ไปยังมือถือ

1. **BLE Service:** เขียน Code ให้ ESP32 ส่งค่า **Risk Vector** (ไม่ใช่แค่ Flag) ผ่าน BLE Notification  
2. **App Test:** เปิดแอปมือถือ (ที่เขียนด้วย MindSpore Lite) เชื่อมต่อ BLE แล้วดูว่าค่ากราฟบน Dashboard ขยับตาม MOCK\_DATA หรือไม่

# App Requirements

# **Part 1: Mobile Application Requirements (SW-SPEC)**

App Name: Smart Clip Companion  
Target OS: Android 10+ (API Level 29+)  
Dev Tools: Android Studio (Kotlin/Java \+ C++ NDK)

### **1\. Functional Requirements (ฟังก์ชันที่ต้องมี)**

#### **Module A: User Interface (UI)**

* **SW-UI-01 (Advanced Dashboard):** หน้าหลักแสดงข้อมูล Real-time เชิงลึก  
  * **Current Risk Score:** เกจวัดความเสี่ยง (0-100%) แยกสี (เขียว/เหลือง/แดง)  
  * **Environmental Vitals:** แสดงค่าปัจจุบันของตัวแปรสำคัญ:  
    * *Pressure Trend:* ลูกศรชี้ลง (หากความดันลด) พร้อมค่า $dP/dt$  
    * *VOC Status:* กราฟเส้นแสดงระดับสารระเหย (Spike Detection)  
    * *Light Condition:* ไอคอนแสดง "Natural Light" หรือ "Artificial Flicker"  
  * **Recent Triggers:** รายการแจ้งเตือนล่าสุด เช่น "10:05 \- Rapid Pressure Drop (-6 hPa)", "10:15 \- 400Hz Drone Noise"  
  * **Connection Status:** ไอคอน BLE (Connected/Disconnected) และ Cloud Sync Status (Last Synced: 10 mins ago)  
* **SW-UI-02 (Symptom Logging \- In-App Feedback):**  
  * ปุ่ม **"I'm feeling unwell"** ขนาดใหญ่  
  * **Popup Form:**  
    * *Phase:* Prodrome / Headache / Postdrome  
    * *Severity:* Slider 1-10  
    * *Specific Triggers (Optional):* ให้ผู้ใช้ติ๊กเลือกสิ่งที่สงสัย (เช่น "ได้กลิ่นฉุน", "แสงแยงตา") เพื่อ Cross-check กับ AI  
* **SW-UI-03 (History & Insights):**  
  * กราฟแท่งแสดงจำนวน Trigger รายวัน/รายสัปดาห์  
  * **Correlation Insight:** ข้อความสรุปจาก Cloud (เช่น "คุณมักปวดหัวเมื่อความดันอากาศลดลงร่วมกับมีกลิ่นฉุน")

#### **Module B: Logic & Processing**

* **SW-LOGIC-01 (BLE Service):** ทำงานแบบ Background Service รับค่า **Feature Vector** (ไม่ใช่ค่าดิบ) จาก Smart Clip ตลอดเวลา  
* **SW-LOGIC-02 (Data Aggregation & Pairing):**  
  * บันทึก Feature Vector ลง Room Database พร้อม Timestamp  
  * เมื่อผู้ใช้กด Log Symptom ให้จับคู่ (Pair) ข้อมูลช่วง 30 นาทีก่อนหน้ากับระดับความปวด เพื่อสร้าง **Labeled Dataset**  
* **SW-LOGIC-03 (PSP Engine \- MindSpore Lite):**  
  * Inference: นำ Vector ล่าสุดเข้าโมเดล .ms เพื่อทำนาย Risk Score  
  * Risk Assessment: หาก Risk Score \> Threshold ให้ส่ง Notification  
* **SW-LOGIC-04 (Cloud Sync & OTA):**  
  * **Upload:** ส่ง Labeled Dataset ขึ้น Huawei Cloud (OBS) เมื่อต่อ Wi-Fi  
  * **Download:** เช็คเวอร์ชันโมเดล .ms บน Cloud และดาวน์โหลดมาติดตั้งอัตโนมัติหากมีเวอร์ชันใหม่ (Personalized Model Update)

#### **Module C: Notification**

* **SW-NOTI-01 (Risk Alert):** "High migraine risk detected\! Rapid pressure drop detected (-6 hPa). Consider resting."  
* **SW-NOTI-02 (Smart Prompt):** หาก Risk สูงต่อเนื่องแต่ผู้ใช้ไม่กด Log ให้ถาม "Are you feeling okay? The environment seems risky."

# **Part 2: Android Project Structure (โครงสร้างไฟล์)**

ใช้โครงสร้าง MVVM \+ Repository Pattern เพื่อรองรับการจัดการข้อมูล Cloud และ BLE ที่ซับซ้อนขึ้น  
Plaintext  
SmartClipApp/  
├── app/  
│   ├── src/  
│   │   ├── main/  
│   │   │   ├── assets/                 \# \[ZONE 1\] เก็บไฟล์โมเดล AI  
│   │   │   │   └── psp\_model\_v1.ms     \# ไฟล์โมเดลเริ่มต้น (Generic Model)  
│   │   │   │  
│   │   │   ├── cpp/                    \# \[ZONE 2\] MindSpore Native Bridge  
│   │   │   │   ├── CMakeLists.txt  
│   │   │   │   ├── native-lib.cpp      \# JNI Interface  
│   │   │   │   └── ms\_lite\_wrapper.cpp \# โค้ดโหลดและรันโมเดล .ms  
│   │   │   │  
│   │   │   ├── java/com/example/smartclip/  
│   │   │   │   ├── data/               \# \[ZONE 3\] Data Layer  
│   │   │   │   │   ├── db/             \# Room Database (เก็บ Log/History)  
│   │   │   │   │   ├── model/          \# Data Classes (FeatureVector, SymptomLog)  
│   │   │   │   │   ├── repository/     \# CloudRepository (จัดการ Upload/Download)  
│   │   │   │   │   └── api/            \# Retrofit Interface (เชื่อมต่อ Huawei Cloud API)  
│   │   │   │   │  
│   │   │   │   ├── service/            \# \[ZONE 4\] Background Services  
│   │   │   │   │   ├── BleService.kt   \# รับค่า Vector จาก ESP32  
│   │   │   │   │   ├── MockBleService.kt \# ★ ตัวจำลองข้อมูล (Gen Pressure Drop/Flicker)  
│   │   │   │   │   └── CloudSyncWorker.kt \# WorkManager สำหรับ Sync ข้อมูลเบื้องหลัง  
│   │   │   │   │  
│   │   │   │   ├── ui/                 \# \[ZONE 5\] Presentation Layer  
│   │   │   │   │   ├── dashboard/      \# แสดงเกจวัดความเสี่ยงและกราฟเส้น  
│   │   │   │   │   ├── log/            \# หน้าบันทึกอาการ  
│   │   │   │   │   └── settings/       \# หน้าเชื่อมต่อ BLE และดูเวอร์ชันโมเดล  
│   │   │   │   │  
│   │   │   │   └── ai/                 \# \[ZONE 6\] AI Logic  
│   │   │   │       └── PSPPredictor.kt \# เรียก JNI เพื่อทำนายผล  
│   │   │   │  
│   │   │   └── res/                    \# Resources (Layouts, Drawables)  
│   │   │  
│   │   └── AndroidManifest.xml         \# Permission: BLUETOOTH, INTERNET, FOREGROUND\_SERVICE  
│   │  
│   └── build.gradle                    \# Dependencies: MindSpore Lite, Room, Retrofit, MPAndroidChart

---

# **🚀 Part 3: Action Plan (เริ่มเขียนยังไงโดยไม่มีของ)**

### **Step 1: สร้าง UI & Data Models**

* **Model:** สร้าง Data Class FeatureVector ให้รองรับฟีเจอร์ใหม่:

Kotlin  
data class FeatureVector(  
    val timestamp: Long,  
    val pressureSlope: Float, // dP/dt  
    val vocSlope: Float,  
    val audio400HzEnergy: Float,  
    val flickerIndex: Float,  
    val heatStressIndex: Float  
)

* **UI:** วาดหน้า Dashboard ให้มีกราฟเส้น (Line Chart) เพื่อโชว์ Trend ของ Pressure และ VOC (ใช้ library MPAndroidChart).

### **Step 2: สร้าง Mock Data Generator (สำคัญมาก)**

อัปเกรด MockBleService.kt ให้สร้างข้อมูลที่สมจริงตาม Scenario:  
Kotlin  
// MockBleService.kt  
val timer \= Timer()  
var currentPressure \= 1013.0f

fun startMocking() {  
    timer.scheduleAtFixedRate(0, 1000) { // ทุก 1 วินาที  
        // จำลอง Pressure Drop Scenario (ลดลงเรื่อยๆ)  
        if (Random.nextBoolean()) currentPressure \-= 0.1f   
          
        val vector \= FeatureVector(  
            timestamp \= System.currentTimeMillis(),  
            pressureSlope \= \-0.1f, // จำลองค่า Slope ติดลบ  
            vocSlope \= if (Random.nextInt(100) \> 90) 50.0f else 0.5f, // 10% โอกาสเกิด VOC Spike  
            audio400HzEnergy \= Random.nextFloat() \* 10,  
            flickerIndex \= 0.02f,  
            heatStressIndex \= 28.0f  
        )  
        sendBroadcast(vector) // ส่งไปแสดงบน UI  
    }  
}

### **Step 3: เตรียม MindSpore Lite Integration**

* เขียน JNI (native-lib.cpp) ให้รับ float\[\] (Feature Vector) เข้าไปคำนวณ  
* (ชั่วคราว) เขียน Logic ใน C++ ให้ Return Risk Score ตาม Rule-based ง่ายๆ ไปก่อน เช่น if (pressureSlope \< \-5) return 80; เพื่อให้ UI ขยับได้โดยไม่ต้องรอโมเดลจริง

### **Step 4: Cloud Sync Skeleton**

* เขียน CloudSyncWorker ให้ลองเชื่อมต่อกับ Huawei OBS Bucket  
* ทดสอบการอัปโหลดไฟล์ text ธรรมดาขึ้น Cloud และทดสอบดาวน์โหลดไฟล์กลับมา เพื่อยืนยันว่า Flow OTA ทำงานได้

# Cloud  Requirements

# **Cloud System Requirements (CL-SPEC)**

Platform: Huawei Cloud  
Core Services: ModelArts (Training), OBS (Storage), API Gateway (Interface)

### **1\. Functional Requirements (ฟังก์ชันที่ระบบคลาวด์ต้องทำ)**

#### **Module A: Data Management (การจัดการข้อมูล)**

* **CL-DATA-01 (Secure Data Ingestion):**  
  * ระบบต้องรองรับการอัปโหลดไฟล์ Log จากแอปพลิเคชันมือถือผ่านโปรโตคอล **HTTPS**  
  * รูปแบบข้อมูล: **Anonymous Labeled Dataset** (Feature Vectors \+ Pain Score) โดยไม่มีข้อมูลระบุตัวตน (PII) หรือไฟล์เสียงดิบ  
* **CL-DATA-02 (Data Segregation):**  
  * จัดเก็บข้อมูลใน **Object Storage Service (OBS)** โดยแยกโฟลเดอร์ตาม Anonymous\_User\_UUID เพื่อป้องกันข้อมูลของผู้ใช้แต่ละคนปะปนกัน  
* **CL-DATA-03 (Data Retention Policy):**  
  * ระบบต้องเก็บข้อมูลย้อนหลังเพียงพอสำหรับการเทรน (เช่น 30 วันล่าสุด) และมีระบบลบข้อมูลเก่าอัตโนมัติ (Lifecycle Management) เพื่อประหยัดพื้นที่และรักษาความเป็นส่วนตัว

#### **Module B: AI Training Pipeline (Huawei ModelArts)**

* **CL-AI-01 (Automated Retraining Trigger):**  
  * ระบบ **ModelArts Workflow** ต้องทำงานอัตโนมัติเมื่อครบกำหนดรอบ (เช่น ทุกๆ 7 วัน หรือเมื่อได้รับ Dataset ใหม่ครบ 100 รายการ)  
* **CL-AI-02 (Personalized Pattern Recognition):**  
  * **Input:** อนุกรมเวลาของ Feature Vectors (Pressure Slope, VOC Slope, 400Hz Energy, Flicker Index) \+ ระดับความเจ็บปวดจริงของผู้ใช้  
  * **Process:** เทรนโมเดล (GRU/TCN) เพื่อหาความสัมพันธ์เฉพาะบุคคล (Correlations)  
    * *Example Logic:* หากพบว่า Pressure Drop \< \-5 สัมพันธ์กับ Pain Level \> 7 บ่อยครั้ง โมเดลจะปรับเพิ่มน้ำหนัก (Weight) ให้กับตัวแปร Pressure  
* **CL-AI-03 (Model Evaluation):**  
  * ก่อนนำไปใช้งาน ระบบต้องเทียบผลลัพธ์กับโมเดลเวอร์ชันเก่า หากโมเดลใหม่มีความแม่นยำสูงกว่า (Loss ต่ำกว่า) จึงจะอนุมัติให้ Deploy

#### **Module C: Model Deployment & OTA (การส่งมอบโมเดล)**

* **CL-DEP-01 (MindSpore Lite Conversion):**  
  * หลังจากเทรนเสร็จ ระบบต้องใช้ **MindSpore Lite Converter** แปลงไฟล์โมเดลจาก Checkpoint (.ckpt) ให้เป็นฟอร์แมต **.ms** ที่รันบนมือถือได้  
* **CL-DEP-02 (Versioning Control):**  
  * ระบบต้องสร้าง Version Tag ให้กับไฟล์โมเดล (เช่น model\_v1.0.ms, model\_v1.1.ms)  
* **CL-DEP-03 (OTA API Endpoint):**  
  * เตรียม API (เช่น GET /api/v1/model/latest) ให้แอปมือถือเรียกตรวจสอบและดาวน์โหลดไฟล์โมเดลล่าสุดได้

### **2\. Cloud Process Flow (ขั้นตอนการทำงานบนคลาวด์)**

กระบวนการทำงานจะเป็นลักษณะ **Batch Processing** (ประมวลผลเป็นรอบๆ ไม่ใช่ Real-time) เพื่อประหยัดทรัพยากร:

1. **Ingest:** Mobile App อัปโหลดไฟล์ CSV (Logs) ไปที่ OBS Bucket: s3://smartclip-data/{user\_id}/logs/  
2. **Trigger:** Huawei FunctionGraph (Serverless) ตรวจจับไฟล์ใหม่ แล้วสั่งเริ่มงาน ModelArts Training Job  
3. **Train:** ModelArts ดึงข้อมูล \-\> ปรับจูนโมเดล (Fine-tuning) \-\> วัดผล (Validation)  
4. **Convert:** หากผลผ่านเกณฑ์ \-\> แปลงเป็น .ms \-\> บันทึกลง OBS: s3://smartclip-models/{user\_id}/latest.ms  
5. **Distribute:** Mobile App ตรวจพบไฟล์ใหม่ \-\> ดาวน์โหลดไปใช้งานทันที

### **3\. Non-Functional Requirements (ข้อกำหนดเชิงคุณภาพ)**

#### **3.1 Security & Privacy (ความปลอดภัย)**

* **CL-SEC-01:** การรับส่งข้อมูลทั้งหมดต้องเข้ารหัสด้วย **TLS 1.2/1.3**  
* **CL-SEC-02:** ข้อมูลบน Cloud (At Rest) ต้องถูกเข้ารหัสด้วย **KMS (Key Management Service)**  
* **CL-SEC-03:** ห้ามระบุชื่อ, อีเมล, หรือข้อมูลส่วนตัวใน Dataset (ใช้ UUID เท่านั้น)

#### **3.2 Scalability & Cost (การขยายตัวและต้นทุน)**

* **CL-SCL-01:** ใช้สถาปัตยกรรม **Serverless** (FunctionGraph) เพื่อเริ่มงานเทรนเฉพาะเมื่อจำเป็น (On-demand) ไม่ต้องเปิด Server ทิ้งไว้ตลอดเวลา ช่วยประหยัดค่าใช้จ่าย  
* **CL-SCL-02:** รองรับการเทรนหลาย User พร้อมกัน (Concurrency) โดยใช้ Container ของ ModelArts แยกกัน

### **4\. ตัวอย่างโครงสร้างข้อมูลบน Cloud (Cloud Data Structure)**

#### **User Log File (CSV in OBS)**

ไฟล์ที่รับมาจากแอปมือถือเพื่อใช้เทรน:  
ข้อมูลโค้ด  
timestamp, dP\_dt, voc\_slope, audio\_400hz, flicker\_idx, pain\_label  
1702512000, \-0.5, 0.01, 10.0, 0.01, 0  
1702515600, \-6.2, 0.02, 12.5, 0.05, 8  
...

#### **Model Versioning (JSON Metadata)**

ไฟล์สำหรับให้แอปเช็คว่าต้องอัปเดตหรือยัง (version.json):  
JSON  
{  
  "user\_id": "a1b2-c3d4-e5f6",  
  "latest\_version": "1.2.0",  
  "last\_updated": "2025-12-14T10:00:00Z",  
  "download\_url": "https://obs.huaweicloud.com/.../model\_v1.2.ms",  
  "changes": "Improved sensitivity to pressure drops."  
}

# Check List

### **✅ Phase 1: AI & Data Generation (ส่วนสมองและข้อมูลจำลอง)**

*เป้าหมาย: ตรวจสอบว่าโมเดลและข้อมูลที่เตรียมไว้ถูกต้อง ก่อนเอาไปใส่ในบอร์ดและแอป*

* \[ \] **Synthetic Data Validity:**  
  * ไฟล์ mock\_data.h มี Array ข้อมูลครบ 4 ชุด (Pressure, VOC, Audio 400Hz, Flicker) หรือไม่?  
  * ลองพล็อตกราฟ (ใน Excel/Python) จากค่าใน Array แล้วกราฟเป็นรูปตามที่คิดไหม? (เช่น Pressure เป็นทางลาดลง, Audio เป็น Sine Wave)  
* \[ \] **Audio Model (MindSpore Micro):**  
  * ไฟล์ audio\_model\_data.h (C Array) ถูกสร้างขึ้นแล้ว  
  * ขนาดโมเดลเล็กพอสำหรับ ESP32 หรือไม่? (ควร \< 150KB)  
* \[ \] **Risk Prediction Model (MindSpore Lite):**  
  * ไฟล์ psp\_model.ms ถูกสร้างขึ้นแล้ว  
  * ทดสอบรันใน PC (ใช้ MindSpore Lite simulate) โดยป้อนค่า Input จำลองเข้าไป \-\> Output ออกมาเป็น Risk Score ที่สมเหตุสมผลหรือไม่?

### **✅ Phase 2: Firmware & Hardware (ส่วนร่างกายและประสาทสัมผัส)**

*เป้าหมาย: ตรวจสอบว่า ESP32 อ่านค่าได้แม่นยำและคำนวณ Logic เชิงลึกได้*

* \[ \] **Electrical Safety Check:**  
  * \[ \] วัดแรงดันไฟ VCC ของเซนเซอร์ทุกตัวว่าได้ **3.3V** (ห้ามเกิน)  
  * \[ \] ต่อสาย I2C (SDA, SCL) และ I2S (SD, SCK, WS) แน่นหนา ไม่หลวม  
* \[ \] **Sensor Drivers (Individual Test):**  
  * \[ \] **BME680:** อ่านค่า Pressure, Temp, Gas Resistance ออกมาเป็นตัวเลขที่ขยับได้เมื่อเป่าลม  
  * \[ \] **INMP441:** อ่านค่า Raw Audio Buffer ออกมาได้ (ลองตะโกนแล้วค่าใน Serial Plotter พุ่งสูงขึ้น)  
  * \[ \] **Photodiode:** ส่องไฟฉายแล้วค่า ADC เปลี่ยนแปลงชัดเจน  
* \[ \] **Advanced Logic Verification (Mock Mode):**  
  * \[ \] เปิด \#define USE\_MOCK\_SENSORS 1  
  * \[ \] ฟังก์ชัน calculate\_pressure\_slope() คืนค่าติดลบ เมื่อป้อนข้อมูลความดันลดลง  
  * \[ \] ฟังก์ชัน FFT คืนค่า Peak ที่ index ตรงกับ 400Hz เมื่อป้อน Sine Wave  
* \[ \] **BLE Packet Structure:**  
  * \[ \] โครงสร้าง struct ในภาษา C ตรงกับ Data Class ใน Kotlin (ลำดับ byte ต้องเหมือนกันเป๊ะ)  
  * \[ \] ทดสอบใช้แอป **nRF Connect** เชื่อมต่อ \-\> เห็นค่า Hex String ไหลเข้ามาถูกต้อง

### **✅ Phase 3: Mobile Application (ส่วนหน้าด่าน)**

*เป้าหมาย: ตรวจสอบว่าแอปฯ รับค่า ประมวลผล และแสดงผลได้*

* \[ \] **BLE Receiver:**  
  * \[ \] สแกนเจออุปกรณ์ชื่อ "SmartClip"  
  * \[ \] เมื่อเชื่อมต่อแล้ว แอปฯ ไม่ Crash  
  * \[ \] แปลงค่า Hex String กลับมาเป็นตัวเลขทศนิยม (Float) ได้ถูกต้อง  
* \[ \] **Inference Engine (MindSpore Lite):**  
  * \[ \] โหลดไฟล์ .ms จาก Assets ได้สำเร็จ (ไม่ขึ้น Error: Model Load Failed)  
  * \[ \] เมื่อรับค่า Vector เข้ามา \-\> เกจวัดความเสี่ยงขยับเปลี่ยนสีได้  
* \[ \] **Database (Room):**  
  * \[ \] เมื่อรับค่าใหม่ \-\> ข้อมูลถูกเขียนลง Database (เช็คผ่าน App Inspection ใน Android Studio)  
  * \[ \] เมื่อกดปุ่ม "Log Symptom" \-\> ข้อมูล Feedback ถูกบันทึกคู่กับ Timestamp

### **✅ Phase 4: Cloud & Backend (ส่วนเรียนรู้)**

*เป้าหมาย: ตรวจสอบท่อส่งข้อมูล (Pipeline) ว่าไม่ตัน*\*

* \[ \] **Credentials:**  
  * \[ \] Access Key (AK/SK) ในแอปฯ ใช้งานได้จริง (ไม่ขึ้น 403 Forbidden)  
  * \[ \] ชื่อ Bucket ในโค้ดตรงกับที่สร้างไว้บน Huawei Cloud  
* \[ \] **Upload Test:**  
  * \[ \] กดปุ่ม Sync ในแอป \-\> ไฟล์ CSV ไปโผล่ใน OBS Console  
* \[ \] **Download (OTA) Test:**  
  * \[ \] ลองอัปโหลดไฟล์ model\_v2.ms (ไฟล์หลอกๆ) ขึ้นไปบน Cloud  
  * \[ \] แอปฯ ตรวจเจอเวอร์ชันใหม่ และดาวน์โหลดมาทับไฟล์เดิมใน Local Storage ได้

### **🚀 Phase 5: The "Hello World" Integration (ประกอบร่าง)**

*เมื่อผ่าน 4 เฟสด้านบนแล้ว ให้ทำตามขั้นตอนนี้:*

1. **Flash Firmware:** อัดโค้ดลง ESP32 (ปิด Mock Mode \-\> ใช้ Real Sensor)  
2. **Install App:** ลงแอปในมือถือ  
3. **Power On:** เปิดสวิตช์ Smart Clip  
4. **Connect:** กด Connect ในแอป  
5. **Stimulate:**  
   * **Action:** ลองเป่าลมใส่ BME680 ยาวๆ (จำลอง Pressure เปลี่ยน) หรือเปิดแอพ Tone Gen 400Hz ใส่ไมค์  
   * **Expectation:** หน้าปัดในแอปต้องขยับเข็มไปโซนสีเหลือง/แดง  
   * **Feedback:** กดปุ่ม "ปวดหัว"  
   * **Cloud Check:** เข้าไปดูใน Huawei Cloud ว่ามี Log ของเหตุการณ์เมื่อกี้ขึ้นไปหรือยัง

[image1]: <data:image/png;base64,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>

[image2]: <data:image/png;base64,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>

[image3]: <data:image/png;base64,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>