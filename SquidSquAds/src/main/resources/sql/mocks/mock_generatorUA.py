# coding=utf-8
# http://faker.readthedocs.io/en/master/
from faker import Faker
import random

fake = Faker()

sqlString = ""

def find_between(s, first, last):
    try:
        start = s.index(first) + len(first)
        end = s.index(last, start)
        return s[start:end]
    except ValueError:
        return ""

def find_before(s,first):
    try:
        start = s.index(first) + len(first)        
        return (s[start:len(s)])
    except ValueError:
        return ""

counter = 0
counter_max = 500
for _ in range(counter_max):
    counter +=1
    sqlString +="INSERT INTO public.agentutilisateur (id_agentutilisateur, agentutilisateurbrut, versionnavigateur, systeme_operation, information_navigateur, plateforme, information_plateforme, extension_navigateur, navigateur, date_heure) VALUES (DEFAULT, "

    ua = fake.user_agent()

    sqlString += "'" + ua #user agent
    sqlString +="','"

    sqlString+= str(random.randint(4, 300)) + "." + str(random.randint(24, 100)) + "." + str(random.randint(1, 80)) #version
    sqlString += "','"

    sqlString += find_between(ua, "(", ")") #os
    sqlString += "','"
    sqlString += find_before(ua, ") ")  # browser
    sqlString += "','"
    
    platform = ['win32', 'win64', 'mac os', 'linux', 'android', 'ios'] #platform
    sqlString += random.choice(platform)
    sqlString += "','"

    info_platform = ['x32', 'x64'] #info_plateforme
    sqlString += random.choice(info_platform)
    sqlString += "','"

    extension = ['Chrome PDF Plugin', 'Native Client', 'Shockwave Flash','Widevine Content Decryption Module'] #browser plugins
    sqlString += str(random.sample(extension, random.randint(1, len(extension)))).replace("'","")
    sqlString += "','"

    browser = ['Chrome','Chromium','Safari','Firefox','Opera'] #browser
    sqlString += random.choice(browser)
    sqlString += "','"

    sqlString += str(fake.date_time_between(start_date="-200d", end_date="now", tzinfo=None))

    sqlString +="');\n"

    print(str(counter) + " / " + str(counter_max))

with open('userAgent.sql', 'w') as file:
    file.write(sqlString)


