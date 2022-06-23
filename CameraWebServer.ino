#include "esp_camera.h"
#include <WiFi.h>
#include <WiFiAP.h>
#include <WiFiManager.h>
#include <WiFiClient.h>
#include "FirebaseESP32.h"
#include <SoftwareSerial.h>
//
SoftwareSerial mySerial(GPIO_NUM_15, GPIO_NUM_12); // RX, TX
//SoftwareSerial mySerial(GPIO_NUM_15, GPIO_NUM_14); // RX, TX



#define CAMERA_MODEL_AI_THINKER // Has PSRAM
#define FIREBASE_HOST "timer-and-monitoring-device-default-rtdb.firebaseio.com/" //dia chi firebase
#define FIREBASE_AUTH "QcZeY6cmpjsDpnYOGvVVjOgNDxS4UC045ova52vJ" //ma xac nhan
FirebaseData firebaseData;
WiFiManager wifimanager;

#include "camera_pins.h"

String kl_l1;
String kl_l2;
String kl_l3;
String kl_l1_tam;
String kl_l2_tam;
String kl_l3_tam;
String tg_l1="";
String tg_l1_tam="";
String tg_l2="";
String tg_l2_tam="";
String tg_l3="";
String tg_l3_tam="";
byte moc;


IPAddress static_IP(192, 168, 1, 109);
IPAddress gateway(192, 168, 1, 100);
IPAddress subnet(255, 255, 255, 0);
IPAddress dns(203, 113, 188, 1);

void startCameraServer();

void setup() {
  mySerial.begin(9600);
  mySerial.println(' '); ////////////////////////////BAT BUOC
//  delay(1000);
//  mySerial.println("TEST");
  Serial.begin(115200);
  Serial.setDebugOutput(true);
  Serial.println();

  camera_config_t config;
  config.ledc_channel = LEDC_CHANNEL_0;
  config.ledc_timer = LEDC_TIMER_0;
  config.pin_d0 = Y2_GPIO_NUM;
  config.pin_d1 = Y3_GPIO_NUM;
  config.pin_d2 = Y4_GPIO_NUM;
  config.pin_d3 = Y5_GPIO_NUM;
  config.pin_d4 = Y6_GPIO_NUM;
  config.pin_d5 = Y7_GPIO_NUM;
  config.pin_d6 = Y8_GPIO_NUM;
  config.pin_d7 = Y9_GPIO_NUM;
  config.pin_xclk = XCLK_GPIO_NUM;
  config.pin_pclk = PCLK_GPIO_NUM;
  config.pin_vsync = VSYNC_GPIO_NUM;
  config.pin_href = HREF_GPIO_NUM;
  config.pin_sscb_sda = SIOD_GPIO_NUM;
  config.pin_sscb_scl = SIOC_GPIO_NUM;
  config.pin_pwdn = PWDN_GPIO_NUM;
  config.pin_reset = RESET_GPIO_NUM;
  config.xclk_freq_hz = 20000000;
  config.pixel_format = PIXFORMAT_JPEG;
  
  // if PSRAM IC present, init with UXGA resolution and higher JPEG quality
  //                      for larger pre-allocated frame buffer.
  if(psramFound()){
    config.frame_size = FRAMESIZE_UXGA;
    config.jpeg_quality = 10;
    config.fb_count = 2;
  } else {
    config.frame_size = FRAMESIZE_SVGA;
    config.jpeg_quality = 12;
    config.fb_count = 1;
  }

#if defined(CAMERA_MODEL_ESP_EYE)
  pinMode(13, INPUT_PULLUP);
  pinMode(14, INPUT_PULLUP);
#endif

  // camera init
  esp_err_t err = esp_camera_init(&config);
  if (err != ESP_OK) {
    Serial.printf("Camera init failed with error 0x%x", err);
    return;
  }

  sensor_t * s = esp_camera_sensor_get();
  // initial sensors are flipped vertically and colors are a bit saturated
  if (s->id.PID == OV3660_PID) {
    s->set_vflip(s, 1); // flip it back
    s->set_brightness(s, 1); // up the brightness just a bit
    s->set_saturation(s, -2); // lower the saturation
  }
  // drop down frame size for higher initial frame rate
  s->set_framesize(s, FRAMESIZE_QVGA);

#if defined(CAMERA_MODEL_M5STACK_WIDE) || defined(CAMERA_MODEL_M5STACK_ESP32CAM)
  s->set_vflip(s, 1);
  s->set_hmirror(s, 1);
#endif

  if(WiFi.config(static_IP, gateway, subnet, dns, dns)==false){
    Serial.println("Cấu hình thất bại");  
  }
  wifimanager.autoConnect("ESP32-Cam");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);  

  startCameraServer();

  Serial.print("Camera Ready! Use 'http://");
  Serial.print(WiFi.localIP());
  Serial.println("' to connect");
}

void loop() {

  if(Firebase.getString(firebaseData,"/Lan 1/Thoi gian cho an"))   
  {
    tg_l1=firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData,"/Lan 1/Khoi luong")) 
  {
//    kl_l1=firebaseData.intData();
//    Serial.println("K.l lan 1: " +kl_l1);
    kl_l1=firebaseData.stringData();
//    Serial.println("K.l lan 1 string: " +kl_l1);
  }
  if(tg_l1.indexOf(':')>=0 && kl_l1.indexOf(' ')<0 && (tg_l1!=tg_l1_tam || kl_l1!=kl_l1_tam))
  {
      Serial.println("T.g lan 1: " +tg_l1); ////////////////////////////
      tg_l1_tam=tg_l1;
      Serial.println("K.l lan 1: " +kl_l1); ///////////////////////
      kl_l1_tam=kl_l1;
      ///////////////////
      for (int i = 0; i < tg_l1.length(); i++) {
        if (tg_l1.charAt(i) == ':') {
            moc = i; //Tìm vị trí của dấu ":"
        }
      }
      String gio_l1 = tg_l1;
      String phut_l1 = tg_l1;
      gio_l1.remove(moc); 
      phut_l1.remove(0, moc + 1); 
      mySerial.println(gio_l1);
      delay(500);
      mySerial.println(phut_l1);
      delay(500);
      mySerial.println(kl_l1);
      delay(500);
  }
        


//=============  

  if(Firebase.getString(firebaseData,"/Lan 2/Thoi gian cho an"))   
  {
    tg_l2=firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData,"/Lan 2/Khoi luong")) 
  {
    kl_l2=firebaseData.stringData();
  }
  if(tg_l2.indexOf(':')>=0 && kl_l2.indexOf(' ')<0 && (tg_l2!=tg_l2_tam || kl_l2!=kl_l2_tam))
  {
      Serial.println("T.g lan 2: " +tg_l2); ////////////////////////////
      tg_l2_tam=tg_l2;
      Serial.println("K.l lan 2: " +kl_l2); ///////////////////////
      kl_l2_tam=kl_l2;
      ///////////////
      for (int i = 0; i < tg_l2.length(); i++) {
        if (tg_l2.charAt(i) == ':') {
            moc = i; //Tìm vị trí của dấu ":"
        }
      }
      String gio_l2 = tg_l2;
      String phut_l2 = tg_l2;
      gio_l2.remove(moc); 
      phut_l2.remove(0, moc + 1); 
      mySerial.println(gio_l2);
      delay(500);
      mySerial.println(phut_l2);
      delay(500);
      mySerial.println(kl_l2);
      delay(500);
  }

//==================================
  if(Firebase.getString(firebaseData,"/Lan 3/Thoi gian cho an"))   
  {
    tg_l3=firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData,"/Lan 3/Khoi luong")) 
  {
    kl_l3=firebaseData.stringData();
  }
  if(tg_l3.indexOf(':')>=0 && kl_l3.indexOf(' ')<0 && (tg_l3!=tg_l3_tam || kl_l3!=kl_l3_tam))
  {
      Serial.println("T.g lan 3: " +tg_l3); ////////////////////////////
      tg_l3_tam=tg_l3;
      Serial.println("K.l lan 3: " +kl_l3); ///////////////////////
      kl_l3_tam=kl_l3;
      ////////////////
      for (int i = 0; i < tg_l3.length(); i++) {
        if (tg_l3.charAt(i) == ':') {
            moc = i; //Tìm vị trí của dấu ":"
        }
      }
      String gio_l3 = tg_l3;
      String phut_l3 = tg_l3;
      gio_l3.remove(moc); 
      phut_l3.remove(0, moc + 1); 
      mySerial.println(gio_l3);
      delay(500);
      mySerial.println(phut_l3);
      delay(500);
      mySerial.println(kl_l3);
      delay(500);
  }

//===================================
  delay(500);
}
