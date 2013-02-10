Personal log4j appenders
------------------------

Right now, there is only one called TimestampFileAppender. It is straightforward. It creates a log file where it appends all the log entries to the end of the file. The file name gets a timestamp appended corresponding to the time of creation of the file. It is particularly useful when you want a log file per run.
