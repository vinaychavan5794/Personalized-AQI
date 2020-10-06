# Personalized-AQI

Following backend files were deployed on AWS EC2 instance:

#### dbproject.py 
#### model.joblib
#### package-lock.json
#### package.json
#### server.js


1.) Air-Quality-And-Heart-Rate-Prediction/Trained_Machine_Learning_Model/ server.js : Server.js has the backend code of Rest API written in Node.js. The android application sends input data (user speed, user direction, wind speed, wind direction, pollutant concentration,age, gender,activity being performed, resting heart rate) to server.js  which in turn sends the data to dbproject.py for further processing.

2.)Air-Quality-And-Heart-Rate-Prediction/Trained_Machine_Learning_Model/dbproject.py: It feeds the data received from server.js into the trained model model.joblib. The model returns a predicted heart rate based on the input and server.js sends the heart rate back to the android application in json format.

3.)Air-Quality-And-Heart-Rate-Prediction/AirQualityPredictionApplication: It is an android application which computes personalised AQI based on different parameters such as wind speed, wind direction, user’s speed, user’s direction, age, gender, pollutant concentratio, heart rate of the user and the activity being performed.

## Credits

https://github.com/HugoWang/IODetector

https://github.com/vinayk011/FitBitWebApiIntegration
