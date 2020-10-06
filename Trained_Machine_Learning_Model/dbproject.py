import sys 
from joblib import load

def rate_predict(model_filepath,data):
    model = load(model_filepath)
    return model.predict(data)

if __name__=='__main__':
    age = sys.argv[1]
    wind_speed = sys.argv[2]
    wind_direction = sys.argv[3]
    user_speed = sys.argv[4]
    user_direction = sys.argv[5]
    gender = sys.argv[6]
    activity = sys.argv[7]
    ihr = sys.argv[8]
    
    data = [int(age),float(wind_speed),int(wind_direction),float(user_speed),int(user_direction)]
    
    if gender == 'M':
        data.append(0)
        data.append(1)
    else:
        data.append(1)
        data.append(0)

    if activity == "C":
        data.append(1)
        data.append(0)
        data.append(0)
    elif activity == "R":
        data.append(0)
        data.append(1)
        data.append(0)
    else:
        data.append(0)
        data.append(0)
        data.append(1)

    data.append(int(ihr))

    model_filepath = './model.joblib'

    fhr = rate_predict(model_filepath, [data])
    print(int(fhr))