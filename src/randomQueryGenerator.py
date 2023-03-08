import random
import string
import datetime

for i in range(200):
    random_number1 = str(random.randint(1, 1000))
    random_number2 = str(random.randint(1, 1000))
    length = random.randint(1, 10)  # generate a random length between 1 and 10
    random_string1 = ''.join(random.choices(string.ascii_letters + string.digits, k=length))
    random_string2 = ''.join(random.choices(string.ascii_letters + string.digits, k=length))
    start_date = datetime.date(2000, 1, 1)
    end_date = datetime.date(2099, 12, 31)
    delta = end_date - start_date
    random_days1 = random.randint(0, delta.days)
    random_date1 = start_date + datetime.timedelta(days=random_days1)
    random_days2 = random.randint(0, delta.days)
    random_date2 = start_date + datetime.timedelta(days=random_days2)

    # format the date as dd/mm/yyyy
    random_date_formatted1 = random_date1.strftime('%d/%m/%Y')
    random_date_formatted2 = random_date2.strftime('%d/%m/%Y')
    print("insert into cs20b012_table2 values "+random_number1+","+random_number2+",\""+random_string1+"\",\""+random_string2+"\","+random_date_formatted1+","+random_date_formatted2)