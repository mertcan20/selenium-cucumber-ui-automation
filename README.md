# Selenium UI Test Otomasyonu

Java, Selenium, Cucumber, JUnit ve Maven ile hazırlanmış web test projesi.

## Projede ne var?

| Akış | Ne yapar? |
| --- | --- |
| Yerel testler | Bilgisayarda açılan örnek mağazada giriş, sepet, miktar, stok ve sipariş kontrollerini çalıştırır. N11'e bağlanmaz. |
| N11 kayıt → giriş | Yeni kullanıcının kayıt formunu doldurur. Sen kayıt/doğrulama adımlarını tamamlayınca temiz tarayıcıda aynı hesapla giriş yapar. |

**Durum:** Yereldeki 10 test geçti; [ilk GitHub Actions koşumu da başarılı](https://github.com/mertcan20/selenium-cucumber-ui-automation/actions/runs/39491585591). Canlı N11 kayıt → giriş akışı henüz uçtan uca doğrulanmadı.

## Yerel testleri çalıştırma

Java JDK 17 veya üzeri, Maven ve Chrome gerekli. IntelliJ'de `pom.xml` bulunan proje klasörünü aç. Terminalde:

```text
mvn clean verify
```

Bu komut 9 mağaza senaryosu ve 1 yerel kayıt/giriş kontrolünü çalıştırır. Tarayıcı arka planda çalışır.

## N11 kayıt → giriş akışını çalıştırma

IntelliJ terminalin **Windows CMD** ise aşağıdaki örnek değerleri kendi test bilgilerinle değiştir:

```bat
set "N11_REGISTER_FIRST_NAME=ADIN"
set "N11_REGISTER_LAST_NAME=SOYADIN"
set "N11_REGISTER_EMAIL=SANA_AIT_YENI_EPOSTA"
set "N11_REGISTER_PHONE=SANA_AIT_TELEFON"
set "N11_REGISTER_PASSWORD=TEST_SIFREN"
mvn -Dtest=runner.TestRunner -Dcucumber.filter.tags=@register-login -Dheadless=false test
```

E-posta yeni üyelik için kullanılabilir olmalı; telefona erişebilmelisin. Şifre, N11 formundaki kurallara uymalı.

Tarayıcı açıldığında script kayıt formunu doldurur. **Üyelik sözleşmesi, Üye Ol gönderimi ve varsa SMS/CAPTCHA adımlarını sen tamamlarsın.** Script en fazla 180 saniye kayıt oturumunun açılmasını bekler. Sonra tarayıcıyı kapatıp yeni tarayıcıda aynı bilgilerle giriş yapar. Oluşturulan hesap otomatik silinmez.

PowerShell kullanıyorsan ortam değişkeni örnekleri ve ayrıntılar: [Kayıt → giriş rehberi](docs/REGISTER-LOGIN.md).

## Kodları hangi sırayla okuyayım?

1. [N11RegisterLogin.feature](src/test/resources/features/N11RegisterLogin.feature) — senaryonun adımları.
2. [N11RegistrationSteps.java](src/test/java/stepdefinitions/N11RegistrationSteps.java) — adımların Java karşılıkları ve aynı hesabın yeniden kullanılması.
3. [N11RegistrationPage.java](src/test/java/pages/N11RegistrationPage.java) — kayıt alanlarını doldurma.
4. [N11Page.java](src/test/java/pages/N11Page.java) — giriş işlemleri ve oturum kontrolü.

## Sonuçları nerede göreceğim?

- Cucumber senaryoları: `target/cucumber-reports.html`
- Bütün testlerin sonuçları: `target/surefire-reports/`
- GitHub koşumları: [Actions](https://github.com/mertcan20/selenium-cucumber-ui-automation/actions)
- Kapsam ve bilinen eksikler: [Test stratejisi](docs/TEST-STRATEGY.md)
