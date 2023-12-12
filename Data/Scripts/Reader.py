import csv
import pandas as pd
import csv
import pandas as pd

def read_csv_file(file_name):
    data = {}
    
    with open(file_name, 'r') as file:
        reader = csv.DictReader(file)
        
        for row in reader:
            for category, value in row.items():
                if category not in data:
                    data[category] = []
                data[category].append(value)
    
    return data


def convert_pkl_to_csv(pkl_file, csv_file):
    data = pd.read_pickle(pkl_file)
    data.to_csv(csv_file, index=False)


def convert_parquet_to_csv(parquet_file, csv_file):
    data = pd.read_parquet(parquet_file)
    data.to_csv(csv_file, index=False)



def save_array_to_csv(data, file_name):
    with open(file_name, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(data.keys())
        writer.writerows(zip(*data.values()))


def read_parquet_file(parquet_file):
    data = pd.read_parquet(parquet_file)
    categories = data.columns.tolist()
    result = {}
    
    for category in categories:
        result[category] = data[category].tolist()
        result[category] = result[category][:25]
    
    return result


if __name__ == '__main__':
    print(read_parquet_file('reviews.parquet'))
    #v = read_csv_file('reviews.csv')
    #a = {}    
    #for key in v.keys():
    #    a[key] = v[key][:50]
    #save_array_to_csv(a, 'test.csv')


