# SiberVatan - Mobil Uygulama Güvenliği Lab.

Eğitim sürecinde kullandığımız tüm materyaller bu repository'de ilgili klasörler altında toplanacaktır. Eksik veya hatalı bir materyal varsa lütfen bizimle iletişime geçin.

# Challenges 
24.04.2024 tarihine kadar çözülmesi gereken mobil uygulama güvenliği challengeleri.
Challengeler ile ilgili hint (ipucu) bilgileri README dosyasında yazılıdır.


 [Challenge 0x01: Something Went Wrong](https://github.com/Ahmeth4n/sibervatan-mobile-lab/tree/main/challenges/siberVatan0x01).
 
  [Challenge 0x02: Parola Ne?](https://github.com/Ahmeth4n/sibervatan-mobile-lab/tree/main/challenges/siberVatan0x02).


## Programlar ve  Parametreleri

`apktool`:  
java -jar apktool.jar **d** base.apk: 

buradaki d parametresi `decompile` anlamına gelir. vermiş olduğunuz APK dosyasını decompile ederek kaynak kodlarını bulunduğunuz dizine yeni bir klasör açarak çıkartır.

java -jar apktool.jar **b** base/: 

b parametresi build anlamına gelir. bu parametre kaynak kodların bulunduğu dizindeki dosyaları tekrar build alarak APK formatına çevirir.

> eğer kaynak kodların olduğu dizinde bulunuyorsanız sondaki `base/` klasör adını vermeden sadece **b** flagi ile build alabilirsiniz. fakat kaynak kodların bulunduğu dizinde değilseniz kaynak kodun yolunu vermelisiniz.

---

`uber-apk-signer`: 
java -jar uber-apk-signer.jar **--apk** base.apk

buradaki --apk parametresi hangi APK'yı imzalamak istediğimizi temsil eder. imzalamak istediğimiz APK dosyasını buraya parametre olarak vermemiz gerekmektedir.

---

`Frida`: 
frida **-U** "SiberVatan" **-l** test.js

Buradaki **-U** parametresi USB'den bağlı olan cihazı temsil eder. Emülatörünüze/cihazınızda işlem yapmak istiyorsanız `-U` ile bunu belirtmelisiniz.

**-l** parametresi `load` anlamına gelir. Inject olduğunuz uygulamada hangi frida scripti çalıştırmak istiyorsanız dosya yolunu ve adını bu parametreye vermelisiniz.

`` Eğer halihazırda çalışan bir uygulamada işlem yapacaksanız yukarıdaki gibi sadece uygulama adını yazmanız yeterlidir. ``

---

`Frida-ps`: 
frida-ps  **-Uai** 

Cihazınızda çalışan servislerin - uygulamaların listesini almanıza yarayan tooldur.

Buradaki **-U** parametresi USB'den bağlı olan cihazı temsil eder.

U'nun yanındaki `a` parametresi **apps** anlamına gelir. Sadece uygulamaları listelemenize yarar. 

`i` parametresi ise "installed" yani **kurulmuş** olan uygulamalar anlamına gelir.

---

Eğer çalışmayan bir uygulamayı frida script ile beraber çalıştırmak isterseniz aşağıdaki gibi düzenlemeler yapmalısınız;

frida -U **-f** "com.tsgk.lab" -l test.js

-f parametresi ise flash anlamına gelir. Açık olmayan bir uygulamayı frida script ile beraber açıp injection işlemi yapmak için kullanılır.

---

``ADB``:

*adb devices*

Bu komut cihazınızdaki bağlı emülatör ve fiziki cihazlarınızı listeler.

---

*adb shell*

bağlı olan cihazınızda telefonunuzun shelline erişim sağlamanıza yarar.

---

*adb **pull** /telefondan/almak/istediginiz/dosya yerelDosya*

pull komutu cihazınızdan dosya almanıza yarayan komuttur. 

ilk parametresi `telefondan almak istediğiniz dosyanın yoludur`.

ikinci parametre ise `bilgisayarınızda bu dosyayı nasıl kaydetmek` istediğinizi belirten parametredir.

---

*adb **push** telefonaAtmakIstediginizDosya /telefonda/atilacak/klasor/dosyaAdi*

push komutu bilgisayarınızdan cihazınıza dosya göndermenize yarayan komuttur. 

ilk parametresi `bilgisayarınızdan göndermek istediğiniz dosyanın yoludur`.

ikinci parametre ise `telefonunuzda bu dosyayı nasıl kaydetmek` istediğinizi belirten parametredir.

---

``SSL pinning`` ile alakalı kısım:
**Sertifika dosyasını tarayıcıdan alma:**

![sertifika dosyasını alma](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/sslpinning.png?raw=true)

**OpenSSL** komutu;
openssl x509 -in badssl.com.pem -pubkey -noout | openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64

`openssl x509 -in badssl.com.pem -pubkey -noout`

**openssl x509**: X.509 sertifikalarını işlemek için kullandığımız komut
**-in badssl.com.pem**: badssl.com.pem dosyasını input olarak veriyoruz
**-pubkey**: sertifikanın public keyini alıyoruz
**-noout**: sertifikanın diğer önemsiz detaylarını istemediğimizi belirtiyoruz

Çıktı: sertifikanın public keyi

`openssl pkey -pubin -outform der`
bir önceki komuttan gelen public keyi bu komuta yönlendiriyoruz

**openssl pkey**: public keyi işleyeceğimizi söylüyoruz
****-pubin**: inputumuzun bir public key olduğunu söylüyoruz
**-outform der**: çıktının DER formatı olması gerektiğini söylüyoruz

Çıktı: public keyin DER formatındaki versiyonu

`openssl dgst -sha256 -binary`

|: bir önceki komuttan aldığımız DER veriyi bu komuta yönlendiriyoruz
**openssl dgst**: digest yani hash oluşturmak için kullanıyoruz
**-sha256**: hash tipini SHA256 veriyoruz
**-binary**: değeri binary formatında vermesini istiyoruz

Çıktı: sha-256 değerinin binary hali

`openssl enc -base64`

|: önceki komuttan gelen binary - SHA256 değeri burada işliyoruz
**openssl enc**: encryption işlemi yapacağımızı söylüyoruz
**-base64**: encryptionu base64 formatında yapacağımızı söylüyoruz
Çıktı: sertifikamızın SHA256 hashinin Base64 formatındaki hali.


bu komuttan aldığımız çıktıyı SSL Pinning yaparken [SSLPinning.java dosyasını inceleyebilirsiniz](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/basic-detections/app/src/main/java/com/tsgk/lab/SSLPinning.java) kullanabiliriz.
