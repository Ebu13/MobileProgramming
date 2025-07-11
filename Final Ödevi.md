# 📱 Mobil Yazılım Geliştirme Proje Ödevi

---

## 🎯 Amaç

Bu projede öğrenciler, modern mobil uygulama geliştirme tekniklerini ve Firebase hizmetlerini kullanarak kapsamlı bir uygulama geliştirecektir.  
Projenin detayları öğrencilerin kendi yaratıcı fikirlerine bırakılacak, ancak aşağıdaki teknik gereksinimlere uyulması zorunludur.

---

## ✅ Projenin Teknik Gereksinimleri

### 1. CRUD İşlemleri

- Proje kapsamında en az bir veri modeli oluşturulmalıdır.
- Kullanıcı, oluşturulan bu veri modelinde aşağıdaki işlemleri gerçekleştirebilmelidir:
  - **C (Create):** Yeni bir kayıt ekleme.
  - **R (Read):** Kayıtları listeleme ve detay görüntüleme.
  - **U (Update):** Var olan bir kaydı düzenleme.
  - **D (Delete):** Kayıt silme.

---

### 2. Firebase Firestore Kullanımı

- Tüm veri işlemleri (CRUD) **Firebase Firestore** üzerinde gerçekleştirilecektir.
- **Örnek:** Bir "Görev Takip Uygulaması" geliştiriliyorsa, görevler Firestore’da tutulmalıdır.

---

### 3. Firebase Authentication ile Giriş

- Uygulamaya giriş için **Firebase Authentication** kullanılacaktır.
- En az iki farklı giriş yöntemi:
  - **E-posta ve Şifre ile Giriş:** Kullanıcılar hesap oluşturmalı ve bu bilgilerle giriş yapabilmelidir.
  - **Google ile Giriş:** Firebase üzerinden OAuth entegrasyonu yapılmalıdır.

---

### 4. Firebase Storage ile Görüntü İşlemleri

- Kullanıcılar uygulamaya görüntü (fotoğraf) yükleyebilmeli ve bu görüntüleri görüntüleyebilmelidir.

**Yapılması Gerekenler:**
  - Firebase Storage’a görüntü yükleme.
  - Yüklenen görüntülerin URL’lerini Firestore’da saklama.
  - Kullanıcının bu görüntüleri uygulamada görüntüleyebilmesi.

---

### 5. Uygulamanın Yayınlanması

- Proje sonunda uygulama **Google Play Store**'da yayınlanmalıdır.
- Yayınlama mümkün değilse, proje **Firebase Hosting** veya başka bir platformda bir demo ile sunulabilir.  
- O da mümkün değilse bir **APK oluşturarak kendi telefonlarınıza yükleyip sunum yapmalısınız**.
