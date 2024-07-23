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



