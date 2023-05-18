# Temperature_Alerte
# Description du projet
 Alerte température est une application android qui affiche la température provenant d'un senseur.
#Commencer
Ces instructions vous permettront d'obtenir une copie du projet en cours d'excécution sur votre machi locale à des fin de dévelloppement et de test.
#Prérequis
Ce que vous devez installer pour exécuter le logiciel:
. Android Studio
.JDK(Java Developpeement Kit)
.Un émulateur Android ou un appareil physique pour test l'application

# Installation
1.Clonez le dépôt:
 git clone git@github.com:dimateme/Temperature_Alerte.git
2. Ouvrez le projet avaec Android Studio
3. Exécutez l'application sur un émulateur Android ou sur un appareil physique connecté
# Utilisation du client Paho Android pour la conncton MQTT
# Construit avec
.Kotlin
.Android Studio
# Gradle
Ajouter dans le fichier build.gradle
   repositories {
        google()
        jcenter()
        maven {
            url "https://repo.eclipse.org/content/repositories/paho-snapshots/"
        }
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.3'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
