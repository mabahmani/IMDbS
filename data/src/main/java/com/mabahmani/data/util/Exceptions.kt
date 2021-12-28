package com.mabahmani.data.util

sealed class Exceptions: Exception(){
    class NetworkConnectionException: Exceptions()
    class HttpException: Exceptions()
}
