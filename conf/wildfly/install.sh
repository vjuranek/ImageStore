#!/bin/bash

WF_VERSION="${WF_VERSION:-"10.0.0.Final"}"
WF_DOWNLOAD_URL="http://download.jboss.org/wildfly/$WF_VERSION/wildfly-$WF_VERSION.tar.gz"
WF_HOME="${WF_HOME:-"/tmp/wildfly"}"

export POSTGRES_JDBC_VERSION="${POSTGRES_JDBC_VERSION:-"9.4.1208"}"
POSTGRES_JDBC_URL="https://jdbc.postgresql.org/download/postgresql-$POSTGRES_JDBC_VERSION.jar"

function download_wf() {
    wget -O /tmp/wildfly.tar.gz $WF_DOWNLOAD_URL
    tar -zxf /tmp/wildfly.tar.gz -C /tmp
    mv /tmp/wildfly-$WF_VERSION $WF_HOME
    rm /tmp/wildfly.tar.gz
}

function download_psql_jdbc() {
    wget -O /tmp/postgresql-$POSTGRES_JDBC_VERSION.jar $POSTGRES_JDBC_URL
}

function create_wf_posgtres_ds() {
    echo "set POSTGRES_JDBC_VERSION=$POSTGRES_JDBC_VERSION" >> $WF_HOME/bin/.jbossclirc
    $WF_HOME/bin/jboss-cli.sh --file=`pwd`/postgres-module.cli
}

function main() {
    download_wf
    download_psql_jdbc
    create_wf_posgtres_ds
}

main
