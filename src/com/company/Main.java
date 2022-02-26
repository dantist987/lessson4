package com.company;

import java.util.Random;

public class Main {

    static int bossHealth = 700;
    static int bossDamage = 50;
    static String bossDefenceType = "";
    static int[] heroesHealth = {300, 320, 340, 360, 400};
    static int[] heroesDamage = {30, 35, 25, 20, 0};
    static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Archer", "Medic"};
    static int roundCounter = 1;

    public static void main(String[] args) {
        while (isGameOver() != true) {
            round();
            roundCounter++;
        }


    }


    public static void printStatistics() {
        System.out.println("----------------");
        System.out.println("Round: " + roundCounter);
        System.out.println("Boss Health: " + bossHealth);

        for (int i = 0; i < heroesAttackType.length; i++) {

            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]);
        }

        System.out.println("----------------");


    }

    public static void bossHits() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHits() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefenceType) {
                    int randomDamage = new Random().nextInt(26);
                    heroesDamage[i] = heroesDamage[i] - randomDamage;
                    System.out.println(heroesAttackType[i] + " decrease damage on " + randomDamage);
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
            } else {
                if (bossHealth - heroesDamage[i] < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
            }
        }
    }

    public static void medicHeals() {
        int medicheals = heroesHealth[4];
        if (medicheals <= 0) {
            System.out.println("Medic is dead.");
            return;
        }
        int minHealthHero = 0;

        for (int i = 0; i < heroesHealth.length; i++) {

            for (int j = i + 1; j < heroesHealth.length; j++) {
                if (heroesHealth[j] > 0 && heroesHealth[j] < heroesHealth[i]) {
                    minHealthHero = j;
                }
            }

        }


        if (heroesHealth[minHealthHero] < 100) {
            int randomHeal = new Random().nextInt(41);
            heroesHealth[minHealthHero] = heroesHealth[minHealthHero] + randomHeal;
            System.out.println(heroesAttackType[minHealthHero] + " healed up for " + randomHeal);

        }
    }


    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }

        if (allHeroesDead == true) {
            System.out.println("Boss won!!!");
        }

        return allHeroesDead;
    }

    public static void changeDefenceType() {
        int randomAttackType = new Random().nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomAttackType];
        System.out.println("Boss choose: " + bossDefenceType);
    }


    public static void round() {
        // Проверяем жив ли наш босс
        if (bossHealth > 0) {
            // Изменяем неуязвимость к определенному типу атаки
            changeDefenceType();
            // Наносит урон героям
            bossHits();
            medicHeals();
        }

        // Герои наносят урон
        heroesHits();

        // Распечатка статистики
        printStatistics();
    }

}

