import sys
import time
import obd
import datetime

connection = obd.Async()

f = open("Write_to_csv.csv", 'w')

def new_rpm(r):
    now = datetime.datetime.now().strftime("%Y-%m-%d, %H:%M:%S, ")
    f.write(now)
    f.write(str(r.value.magnitude))
    f.write(", rpm\n")
    print(r.value, "\n")

def speed(s):
    now = datetime.datetime.now().strftime("%Y-%m-%d, %H:%M:%S, ")
    f.write(now)
    f.write(str(s.value.magnitude))
    f.write(", speed\n")
    print(s.value, "\n")


def throttle_pos(tp):
    now = datetime.datetime.now().strftime("%Y-%m-%d, %H:%M:%S, ")
    f.write(now)
    f.write(str(tp.value.magnitude))
    f.write(", throttle position\n")
    print(tp.value, "/n")

connection.watch(obd.commands.RPM, callback = new_rpm)
connection.watch(obd.commands.SPEED, callback = speed)
connection.watch(obd.commands.THROTTLE_POS, callback = throttle_pos)

connection.start()

time.sleep(10)
connection.stop()
f.close()