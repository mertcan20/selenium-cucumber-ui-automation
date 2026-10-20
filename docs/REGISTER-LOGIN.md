# N11 register → aynı hesapla login

Kullanıcının istediği tek E2E yolculuğu: kayıt formunu doldur → kayıt/telefon doğrulamasını tamamla → kayıt oturumunu doğrula → tarayıcıyı kapat → temiz tarayıcıda aynı e-posta ve şifre ile giriş yap → oturum açıldığını doğrula.

Bu akış, başka bir testin sırasına veya dosyaya yazılmış şifrelere dayanmaz. `RegistrationUser` yalnız bu senaryonun belleğinde tutulur. Şifreler rapora yazdırılmaz; canlı register senaryosunun hata ekran görüntüsü de kişisel alanları korumak için otomatik alınmaz.

## Kaynaklar

- `features/N11RegisterLogin.feature`: tek yolculuğun adımları.
- `stepdefinitions/N11RegistrationSteps.java`: kullanıcı verisini kayıt ve giriş arasında paylaşır.
- `pages/N11RegistrationPage.java`: form alanlarını doldurur.
- `pages/N11Page.java`: tek veya iki aşamalı giriş; hesap/çıkış menüsü üzerinden oturum kontrolü.
- `support/RegistrationUser.java`: ortamdan okunan, raporlarda gizlenen kullanıcı bilgileri.
- `runner/N11JourneyTestRunner.java`: aynı page object'leri yerel HTTP düzeneğinde sınayan tarayıcı testi.

## Canlı çalıştırma

IntelliJ terminalinde, proje kökünde bu ortam değişkenlerini yalnız kendi bilgilerinle ayarla:

```powershell
$env:N11_REGISTER_FIRST_NAME = "ADIN"
$env:N11_REGISTER_LAST_NAME = "SOYADIN"
$env:N11_REGISTER_EMAIL = "SANA_AIT_YENI_EPOSTA"
$env:N11_REGISTER_PHONE = "SANA_AIT_TELEFON"
$env:N11_REGISTER_PASSWORD = "SECILEN_TEST_SIFRESI"
mvn -B '-Dtest=runner.TestRunner' '-Dcucumber.filter.tags=@register-login' '-Dheadless=false' test
```

Şifre, formda gözlenen 8–15 karakter, en az bir rakam, büyük ve küçük harf koşullarını karşılamalı. Daha önce kayıtlı olmayan, erişebildiğin bir e-posta ve doğrulayabildiğin telefon kullan. `.env` dosyası otomatik okunmaz; değerleri Git'e veya sohbete kopyalama.

Script formu doldurur. Üyelik sözleşmesini inceleyip onaylama, Üye Ol gönderimi ve varsa SMS/CAPTCHA adımlarını **sen** tamamlarsın. Script 180 saniye hesap menüsünü bekler; gerekirse `-Dn11.verificationTimeoutSeconds=300` kullan. Bu nedenle headless kayıt koşumu açık bir hata mesajıyla reddedilir. Kayıt tamamlanmadan login'e geçilmez ve zaman aşımı başarılı sayılmaz.

Hesap açıldığında script ilk tarayıcıyı kapatır. Yeni tarayıcıda kayıt için kullanılan aynı bilgilerle giriş yapar. Böylece kayıt sırasında açılan oturum login testi yerine geçmez. Yeni hesap sitede kalır; otomatik hesap silme yapılmaz. Aynı e-posta ile tekrar çalıştırma yeni üyelik koşulunu karşılamaz.

## Doğrulama ve sınırlar — 20 Ekim 2026

- Canlı sayfalar salt okunur incelendi: kayıt alanlarının `firstName`, `lastName`, `email`, `gsmNumber`, `password` kimlikleri gözlendi.
- Güncel canlı giriş ilk aşamada e-posta/telefon soruyor; ilk butonun `aria-label` değeri `Giriş Yap`. Sonraki şifre aşaması, SMS ve oturum menüsünün güncel davranışı canlı hesapla doğrulanmadı. Hesap menüsü locator'ları önceki projeden gelir; canlı koşumda kontrol edilmeli.
- Canlı N11'e kişisel veri gönderilmedi; hesap açılmadı, sözleşme kabul edilmedi, SMS/CAPTCHA tamamlanmadı.
- Yerel HTTP/bellek test sunucusunda kayıt → temiz tarayıcı → aynı bilgilerle giriş ve yanlış şifre reddi doğrulandı. Bu test canlı N11 backend'ini veya SMS'i doğrulamaz.
- Genel UI koşumu: 9 Cucumber senaryosu + 1 kayıt/giriş page-object kontrolü, 10 test geçti.
- Sözdizimi/adım kontrolü ayrıca dry-run ile yapılır; gerçek E2E başarı iddiası değildir.

Yerel kaynaklar güncellendi; GitHub'a gönderilmedi.
