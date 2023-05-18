package com.example.myapplication
//données pour la connexion au broker mqtt

const val SOLACE_CLIENT_USER_NAME = "paul"
const val SOLACE_CLIENT_PASSWORD = "Savoir7$"
const val SOLACE_MQTT_HOST = "tcp://172.16.5.103:1883"

// Other options
const val SOLACE_CONNECTION_TIMEOUT = 3
const val SOLACE_CONNECTION_KEEP_ALIVE_INTERVAL = 60
const val SOLACE_CONNECTION_CLEAN_SESSION = true
const val SOLACE_CONNECTION_RECONNECT = true