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
counter_max = 100
for _ in range(counter_max):
    counter +=1
    sqlString += "INSERT INTO public.infodesuivi (id_infodesuivi, id_agentutilisateur, id_sitewebadmin, empreinte, urlactuel, urlprovenance, adresse_ipv4, adresse_ipv6, taille_ecran, langue,tempsecoule,date_heure) VALUES (DEFAULT, "

    sqlString += str(random.randint(5, 500))  # id_agentutilisateur
    sqlString += ","

    sqlString += "1"  # id_sitewebadmin

    sqlString += ",'"
    sqlString += fake.md5(raw_output=False)  # empreinte
    sqlString += "','"

    sqlString += fake.uri()  # urlactuel
    sqlString +="','"

    sqlString += fake.uri()  # urlprovenance
    sqlString += "','"

    sqlString += fake.ipv4(network=False)  # adresse_ipv4
    sqlString += "','"

    sqlString += fake.ipv6(network=False)  # adresse_ipv6
    sqlString += "','"

    tailleecran = ['1900x1200', '800x600', '640x480', '768x1024', '412x732']  # taille_ecran
    sqlString += random.choice(tailleecran)
    sqlString += "','"

    langue = ['fr', 'en', 'ca-fr', 'ca-en']  # taille_ecran
    sqlString += random.choice(langue)
    sqlString += "','"

    sqlString += str(random.randint(0, 500))  # tempsecoule
    sqlString += "','"
    sqlString += str(fake.date_time_between(start_date="-200d", end_date="now", tzinfo=None))

    sqlString +="');\n"

    print(str(counter) + " / " + str(counter_max))

with open('infoSuivi.sql', 'w') as file:
    file.write(sqlString)


