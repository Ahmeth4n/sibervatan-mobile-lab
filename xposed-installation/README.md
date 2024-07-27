# Xposed Instlalation

Derste xposed ile alakalı tüm materyalleri buradaki klasörden bulabilirsiniz. Anlatıma geçelim;

Github reposunda bulunan  [rootAVD](https://github.com/Ahmeth4n/sibervatan-mobile-lab/tree/main/rootAVD) dosyasını indirelim ve bir klasöre çıkartalım.

Daha sonra `rootAVD.bat ListAllAVDs` komutunu çalıştıralım.

![xposed step 1](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands1.PNG?raw=true)

Karşınıza bu şekilde emülatörünüzle alakalı 2 alan çıkacak. burada klasörlerin birinde `google_apis_playstore` diğerinde ise `google_apis` geçecektir. siz `google_apis` olan kısımdaki en üst satırı kopyalayın.

Örnek; `rootAVD.bat system-images\android-33\google_apis\x86_64\ramdisk.img`

ardından bu satırı çalıştırdıktan sonra karşınızda işlemler başlayacaktır. bir süre sonra versiyon sorduğunda sadece entera basın ve devam edin.

![xposed step 2](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands2.png?raw=true)

kurulum bittikten sonra son yazı olarak terminalinizde `[!] If the AVD doesnt shut down, try it manually!` yazacaktır. Bu metni gördükten sonra cihazınız rebootlanacaktır.

Rebootlandıktan sonra cihazınızı tekrar açmaya çalışırsanız `Cihaz zaten aktif` hatası alacaksınız. Bu yüzden `Görev Yöneticisini` açalım.


![xposed step 3](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands4.png?raw=true)

Görev yöneticisinde **qemu-system** ile başlayan processi sonlandıralım. Bu bazen Android Studio sekmesinin altında bazen de direkt olarak process olarak gözükebilir. Bunu sonlandırıp, cihazımızı `Cold Boot` modunda tekrar başlatalım.

![xposed step 4](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands3.png?raw=true)

Cihazınızı açtığınızda `Magisk` uygulaması yüklü olacaktır. Tıkladığınızda aşağıdaki gibi bir uyarı verirse `OK` a basalım ve indirmesine izin verelim.

![xposed step 5](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands5.png?raw=true)

Diğer kaynaklardan indirmeye izin vermemiz için bir uyarı alanı daha açılacaktır. Burada **Settings** kısmına basıp dışarıdan uygulama kurmaya izin verelim. 
**Allow from this source** opsiyonunu aktif edelim.

![xposed step 6](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands6.png?raw=true)

ardından tekrar `Update` butonuna tıklayalım.

![xposed step 7](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands7.png?raw=true)

Ardından Magisk uygulamamız başarıyla kurulmuş olacaktır. Magisk uygulamamıza girdiğimizde ilk bölümdeki **Install** butonuna bastığımızda `Direct İnstall (Recommend)` alanı açılmış olacaktır. (eğitimde açılmayan alan :=) )

![xposed step 8](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands8.png?raw=true)

Bu alan geldikten sonra `Direct İnstall (Recommend)` i tıklayıp, **Reboot** a bastığınızda cihazınızı rebootlayacaktır.

Cihaz rebootlandıktan sonra tekrar Magisk'i açtığımızda sağ alt kısımda **Modules** sekmesi aktif hale gelecektir.
![xposed step 9](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands15.png?raw=true)

Bu kısımda ilgili modüllerimizi install etmemiz gerekiyor. Şimdi bu repoda bulunan riru.zip ve LSPosed.zip dosyalarımızı adb push komutu ile cihazımızın `/storage/emulated/0/Download` dizinine gönderelim.

![xposed step 10](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands9.png?raw=true)

Gönderdikten sonra Modules sayfasondan `Install from Storage` bölümüne tıklayalım ve önce `riru.zip` dosyasını yükleyelim ve cihazımızı `Reboot` butonu ile reboot edelim.

![xposed step 11](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands10.png?raw=true)

![xposed step 12](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands11.png?raw=true)


Ardından `LSPosed.zip` için aynı işlemi tekrarlayalım.

Finalde iki modülümüz de aktif olarak kurulmuş şekilde Modules sayfasında alttaki gibi gözükmesi gerekiyor.

![xposed step 13](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands12.png?raw=true)

Şu an herşeyimiz hazır. Tek yapmamız gereken **Xposed Manager'i** kurmak. Az önce kurduğumuz modüldeki `LSPosed.zip` dosyasının içindeki `manager.apk` dosyasını zip'in içinden çıkartalım.

![xposed step 14](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands13.png?raw=true)


manager.apk dosyamızı adb install komutu ile cihazımıza kuralım.

![xposed step 15](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands14.png?raw=true)

kurulum işleminden sonra `adb shell` ile terminalimizden önce **su** komutunu çalıştıralım. ardından `setenforce 0` ile güvenlik önlemlerini devre dışı bırakalım.

Ana ekranımıza gelen LSPosed uygulamamız açalım ve her şey hazır :=)

![xposed step 16](https://github.com/Ahmeth4n/sibervatan-mobile-lab/blob/main/xposed-installation/commands16.png?raw=true)

