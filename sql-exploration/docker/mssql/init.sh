#!/bin/bash

set -xe

function is_up_func {
    /opt/mssql-tools/bin/sqlcmd -l 30 -S localhost -h-1 -V1 -U sa -P "$SA_PASSWORD" -Q "SET NOCOUNT ON SELECT \"WE ARE UP\" , @@servername"
    is_up=$?
}

/opt/mssql/bin/sqlservr &

# Wait for it to be available
echo "Waiting for MS SQL to be available"
is_up=1
while [ $is_up -ne 0 ] ; do
  sleep 10
  is_up_func
done

#-l login_timeout
# Specifies the number of seconds before a sqlcmd login to the ODBC driver times out when you try to connect to a server
#-P password
# Is a user-specified password. Passwords are case-sensitive.

#-U login_id
# Is the login name or contained database user name. For contained database users, you must provide the database name
# option (-d).

#-S [protocol:]server[\instance_name][,port]
# Specifies the instance of SQL Server to which to connect. It sets the sqlcmd scripting variable SQLCMDSERVER.

#-h headers
# Specifies the number of rows to print between the column headings. The default is to print headings one time for each
# set of query results. This option sets the sqlcmd scripting variable SQLCMDHEADERS. Use -1 to specify that headers
# not be printed. Any value that is not valid causes sqlcmd to generate an error message and then exit.

#-V error_severity_level
# Controls the severity level that is used to set the ERRORLEVEL variable. Error messages that have severity levels
# greater than or equal to this value set ERRORLEVEL. Values that are less than 0 are reported as 0. Batch and CMD
# files can be used to test the value of the ERRORLEVEL variable.

#-Q " cmdline query "
# Executes a query when sqlcmd starts and then immediately exits sqlcmd. Multiple-semicolon-delimited queries can be
# executed.

#-e
# Writes input scripts to the standard output device (stdout).

#-i input_file[,input_file2...]
# Identifies the file that contains a batch of SQL statements or stored procedures. Multiple files may be specified
# that will be read and processed in order. Do not use any spaces between file names. sqlcmd will first check to see
# whether all the specified files exist. If one or more files do not exist, sqlcmd will exit.

/opt/mssql-tools/bin/sqlcmd -l 30 -U sa -P "$SA_PASSWORD" -S localhost -h-1 -V1 -e -Q "CREATE DATABASE jpa_exploration"
/opt/mssql-tools/bin/sqlcmd -l 30 -U sa -P "$SA_PASSWORD" -S localhost -h-1 -V1 -e -i /init.sql

# Keep container running
sleep infinity
