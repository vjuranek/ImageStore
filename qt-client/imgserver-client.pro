QT += core network xml
QT -= gui

CONFIG += c++11

TARGET = imgserver-client
CONFIG += console
CONFIG -= app_bundle

TEMPLATE = app

SOURCES += src/imgserver/main.cpp \
     src/imgserver/restclient.cpp

HEADERS += \
     include/imgserver/restclient.h
