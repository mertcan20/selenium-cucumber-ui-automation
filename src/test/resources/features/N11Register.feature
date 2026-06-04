@register
Scenario: Kullanıcı N11 üyelik formunu doldurabilmelidir

  Given Kullanıcı N11 ana sayfasına gider
  When Kullanıcı Üye Ol sekmesine tıklar
  And Kullanıcı Ad alanına geçerli bir ad girer
  And Kullanıcı Soyad alanına geçerli bir soyad girer
  And Kullanıcı E-posta Adresi alanına geçerli bir e-posta girer
  And Kullanıcı Telefon Numarası alanına geçerli bir telefon numarası girer
  And Kullanıcı Şifre alanına geçerli bir şifre girer
  And Kullanıcı Cinsiyet alanından Erkek seçeneğini seçer
  And Kullanıcı Üyelik Sözleşmesi checkboxını işaretler
  Then Numaranı Doğrula butonuna tıklar

