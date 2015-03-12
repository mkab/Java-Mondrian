#!/usr/bin/env bash 
## :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
## ::		Script for backing up orginal *.jsp files 
## ::		and renaming the *.jsp.tmp files to that 
## ::  	of the original ones
## ::		
## ::	Created : 12/06/2012
## ::	Team: Mohammad Kabir ABDULSALAM, Zouleye BACHARD, Eric RUKUNDO
## :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


#Shell script error handling
set -e
set -o pipefail


SUCCESS=0
EXPECTED_ARGS=1
E_BADARGS=2
E_NOTADIR=3

#Begin function createbackup()
createbackup() {
	#Create a folder "backup" to back up all the original *.jsp files
	if [ ! -d $1/backup ]
	then	
		echo "Creating backup folder..."
		mkdir backup
		echo "Folder backup created"
	else
		echo "backup folder already created..."
	fi
 
	#Copy the original jsp files to the backup folder
	echo "Backing up original files"
	
	
	#check which directories exist and create a directory from the last number
	#i=`expr $i + 1`
	while [ -d $QUERIES/backup/old$i ]
	do
		i=`expr $i + 1`
	done
	
	#Create the "old($i)" directory
	echo "Creating old$i directory"
	mkdir backup/old$i	
} #end function createbackup()

#Check if the script has been executed with only 1 argument
if [ $# -ne $EXPECTED_ARGS ]
then
	echo "`basename $0`: missing argument"
  	echo "Usage: `basename $0` {tomcat's installation directory}"
	exit $E_BADARGS
fi

# get the full path to the /webapps directory of tomcat installation
TOMCATHOME="$1"

if [ -d $TOMCATHOME ] 
then
	echo
	echo "Using directory $TOMCATHOME as Tomcat's installation directory"
	QUERIES="$TOMCATHOME/webapps/mondrian/WEB-INF/queries/"

	#Move to the queries directory
	cd $QUERIES
	
	i=0
	#Create necessary folders
	createbackup $QUERIES 
	
	echo "Moving files to old$i in $QUERIES/backup"
	mv arrows.jsp colors.jsp fourhier.jsp mondrian.jsp backup/old$i

	#Rename temporary files
	echo "Renaming temporary jsp files in $QUERIES"
	for file in `ls *.tmp`
	do
		temp=`ls $file | cut -d "." -f1,2`
		echo "renaming $file to $temp";
		mv $file $temp
	done

	#Update the datasources.xml file
	echo
	echo "Updating datasources.xml..."
	XML="$TOMCATHOME/webapps/mondrian/WEB-INF/"

	#Move to the queries directory
	cd $XML

	i=0
	#Create necessary folders
	createbackup $XML
	
	echo "Moving files to old$i in $XML/backup"
	mv datasources.xml backup/old$i

	#Rename temporary files
	echo "Renaming temporary XML file in $XML"
	mv datasources.xml.tmp datasources.xml

else
	echo "Directory doesn't exist";
	exit $E_NOTADIR;
fi

echo "Done."
exit $SUCCESS
