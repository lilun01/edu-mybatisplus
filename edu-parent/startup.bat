title edu-web
java -Xms50m -Xmx50m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./ -XX:+HeapDumpBeforeFullGC -XX:HeapDumpPath=./ -jar edu-web-0.0.1.jar