wget -O master.zip https://github.com/typelevel/sbt-tls-crossproject/archive/master.zip
unzip master.zip
rm master.zip
cd sbt-tls-crossproject-master
sbt publishLocal
