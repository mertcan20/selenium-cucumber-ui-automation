# Selenium UI Test Otomasyonu

Java, Selenium, Cucumber ve JUnit ile geliştirilmiş web test otomasyonu projesi. Senaryolar Gherkin ile yazıldı; sayfa işlemleri ve locator'lar Page Object Model yapısıyla test adımlarından ayrıldı.

## Test kapsamı

- Örnek mağaza üzerinde başarılı ve hatalı giriş, sepet işlemleri, miktar sınırları, stok ve sipariş kontrolleri.
- N11 için kayıt formunun doldurulması ve kayıt tamamlandıktan sonra aynı hesapla yeni tarayıcı oturumunda giriş yapılması.

N11 akışında üyelik onayı ve varsa SMS/CAPTCHA adımları kullanıcı tarafından tamamlanır. Bu akışın canlı N11 üzerinde uçtan uca doğrulaması henüz yapılmadı.

Testlerde explicit wait kullanıldı, her senaryo için tarayıcı temizliği eklendi ve hesap bilgileri ortam değişkenlerine taşındı. Yerel testler GitHub Actions üzerinde çalıştırılıyor; sonuçlar Cucumber ve JUnit raporlarıyla tutuluyor.

## Çalıştırma

Java 17+, Maven ve Chrome ile proje klasöründe:

```bash
mvn clean verify
```

Bu komut yerel ortamda 10 testi çalıştırır. Cucumber raporu `target/cucumber-reports.html`, tüm test sonuçları `target/surefire-reports/` altında oluşur.

N11 hesabıyla çalıştırma adımları: [Kayıt ve giriş rehberi](docs/REGISTER-LOGIN.md).
