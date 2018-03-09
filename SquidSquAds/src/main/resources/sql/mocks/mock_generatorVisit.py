# coding=utf-8
import random
from datetime import datetime, timedelta

sqlString = ""
counter = 0
counter_max = 100


for _ in range(counter_max):
    counter +=1
    sqlString +="INSERT INTO public.visite (id_visite, id_banniere, date_heure, est_cliquee, est_ciblee) VALUES (DEFAULT, "

    sqlString += str(random.randint(1, 3))
    sqlString += ",'"

    start = datetime.now()
    end = start - timedelta(days=600)
    random_date = start + (end - start) * random.random()

    sqlString+= str(random_date)
    sqlString += "',"

    sqlString += str(random.choice([True, False]))
    sqlString += ","

    sqlString += str(random.choice([True, False]))

    sqlString +=");\n"

    print(str(counter) + " / " + str(counter_max))

with open('visite.sql', 'w') as file:
    file.write(sqlString)
