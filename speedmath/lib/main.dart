import 'package:flutter/material.dart';
import 'dart:math';

void main() {
  runApp(const SpeedMath());
}

class SpeedMath extends StatelessWidget {
  const SpeedMath({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: const MathGameScreen(),
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
    );
  }
}

class MathGameScreen extends StatefulWidget {
  const MathGameScreen({super.key});

  @override
  State<MathGameScreen> createState() => _MathGameScreenState();
}

class _MathGameScreenState extends State<MathGameScreen> {
  late int number1;
  late int number2;
  late int correctAnswer;
  late int incorrectAnswer;

  final Random random = Random();

  @override
  void initState() {
    super.initState();
    generateNewQuestion();
  }

  void generateNewQuestion() {
    setState(() {
      number1 = random.nextInt(10);
      number2 = random.nextInt(10);
      correctAnswer = number1 + number2;
      incorrectAnswer = correctAnswer + random.nextInt(5) + 1; // Yanlış cevap, doğru cevaba yakın bir sayı
    });
  }

  void checkAnswer(int answer) {
    bool isCorrect = answer == correctAnswer;
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(isCorrect ? 'Tebrikler!' : 'Bilemediniz!'),
        backgroundColor: isCorrect ? Colors.green : Colors.red,
        duration: const Duration(seconds: 1),
      ),
    ).closed.then((reason) {
      generateNewQuestion();
    });
  }

  @override
  Widget build(BuildContext context) {
    List<int> options = [correctAnswer, incorrectAnswer]..shuffle();

    return Scaffold(
      appBar: AppBar(
        title: const Text('SpeedMath'),
        centerTitle: true,
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text(
                '$number1 + $number2 = ?',
                style: const TextStyle(fontSize: 32, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 40),
              Wrap(
                spacing: 20.0,
                runSpacing: 20.0,
                children: options.map((option) {
                  return ElevatedButton(
                    onPressed: () => checkAnswer(option),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.white54,
                      padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 16),
                      textStyle: const TextStyle(fontSize: 24),
                    ),
                    child: Text('$option'),
                  );
                }).toList(),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
