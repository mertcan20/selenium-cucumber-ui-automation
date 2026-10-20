@external @register-login @manual-verification
Feature: Yeni hesabı oluşturup aynı hesapla giriş yapmak
  # One journey; credentials stay in this scenario, not in another test's output.
  # Live consent, account submission and any SMS/CAPTCHA are completed by the operator.
  Scenario: Yeni kullanıcı kaydolduktan sonra temiz tarayıcıda giriş yapabilir
    Given yeni kullanıcı bilgileri ortam değişkenlerinden hazırlanır
    And N11 kayıt formu açılır
    When kayıt formu yeni kullanıcı bilgileriyle doldurulur
    And kullanıcı üyelik onayını ve gerekli doğrulamaları tamamlar
    Then yeni hesap için oturum açıldığı doğrulanır
    When kayıt tarayıcısı kapatılıp temiz tarayıcıda giriş sayfası açılır
    And aynı yeni kullanıcının e-posta ve şifresiyle giriş yapılır
    Then yeni hesap için oturum açıldığı doğrulanır
