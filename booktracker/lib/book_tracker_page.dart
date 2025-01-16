import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:booktracker/login_page.dart';

class BookTrackerPage extends StatefulWidget {
  const BookTrackerPage({super.key});

  @override
  State<BookTrackerPage> createState() => _BookTrackerPageState();
}

class _BookTrackerPageState extends State<BookTrackerPage> {
  final CollectionReference booksCollection =
  FirebaseFirestore.instance.collection('books');
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _authorController = TextEditingController();

  Future<void> addBook(String title, String author) async {
    try {
      await booksCollection.add({
        'title': title,
        'author': author,
        'userId': FirebaseAuth.instance.currentUser?.uid,
        'timestamp': FieldValue.serverTimestamp(),
      });
    } catch (e) {
      _showErrorSnackbar('Kitap eklenirken hata oluştu: $e');
    }
  }

  Future<void> updateBook(String docId, String title, String author) async {
    try {
      await booksCollection.doc(docId).update({
        'title': title,
        'author': author,
      });
    } catch (e) {
      _showErrorSnackbar('Kitap güncellenirken hata oluştu: $e');
    }
  }

  Future<void> deleteBook(String docId) async {
    try {
      await booksCollection.doc(docId).delete();
    } catch (e) {
      _showErrorSnackbar('Kitap silinirken hata oluştu: $e');
    }
  }

  void _showErrorSnackbar(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message), backgroundColor: Colors.redAccent),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Kitap Takibi'),
        backgroundColor: Colors.blueAccent,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () async {
              await FirebaseAuth.instance.signOut();
              Navigator.pushAndRemoveUntil(
                context,
                MaterialPageRoute(builder: (context) => LoginPage()),
                    (route) => false,
              );
            },
          ),
        ],
      ),
      body: StreamBuilder<QuerySnapshot>(
        stream: booksCollection
            .where('userId',
            isEqualTo: FirebaseAuth.instance.currentUser?.uid)
            .orderBy('timestamp', descending: true)
            .snapshots(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return const Center(child: Text('Bir hata oluştu.'));
          }
          if (!snapshot.hasData || snapshot.data!.docs.isEmpty) {
            return const Center(child: Text('Henüz kitap eklenmedi.'));
          }
          final books = snapshot.data!.docs;
          return ListView.builder(
            padding: const EdgeInsets.all(12.0),
            itemCount: books.length,
            itemBuilder: (context, index) {
              final book = books[index];
              return Card(
                elevation: 4,
                margin: const EdgeInsets.symmetric(vertical: 8),
                child: ListTile(
                  title: Text(book['title'], style: const TextStyle(fontWeight: FontWeight.bold)),
                  subtitle: Text(book['author']),
                  trailing: Wrap(
                    spacing: 8,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.edit, color: Colors.blue),
                        onPressed: () {
                          _titleController.text = book['title'];
                          _authorController.text = book['author'];
                          _showEditDialog(book.id);
                        },
                      ),
                      IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () => deleteBook(book.id),
                      ),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.blueAccent,
        onPressed: () => _showAddDialog(),
        child: const Icon(Icons.add),
      ),
    );
  }

  void _showAddDialog() {
    _titleController.clear();
    _authorController.clear();
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Kitap Ekle'),
        content: _buildDialogContent(),
        actions: _buildDialogActions(() => addBook(
          _titleController.text,
          _authorController.text,
        )),
      ),
    );
  }

  void _showEditDialog(String docId) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Kitap Güncelle'),
        content: _buildDialogContent(),
        actions: _buildDialogActions(() => updateBook(
          docId,
          _titleController.text,
          _authorController.text,
        )),
      ),
    );
  }

  Widget _buildDialogContent() {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        TextField(controller: _titleController, decoration: const InputDecoration(labelText: 'Başlık')),
        TextField(controller: _authorController, decoration: const InputDecoration(labelText: 'Yazar')),
      ],
    );
  }

  List<Widget> _buildDialogActions(VoidCallback onSubmit) {
    return [
      TextButton(
        onPressed: () => Navigator.pop(context),
        child: const Text('İptal'),
      ),
      ElevatedButton(
        onPressed: () {
          onSubmit();
          Navigator.pop(context);
        },
        child: const Text('Kaydet'),
      ),
    ];
  }
}
