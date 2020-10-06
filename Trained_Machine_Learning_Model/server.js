var http = require('http');
var express = require('express'); 
var app = express(); 
var bodyParser = require('body-parser')
const mysql = require('mysql');


const con = mysql.createPool({
    host: "XXXXX",
    user: "XXXX",
    password: "XXXXX",
    connectionLimit : 100,
    database : 'XXXX'
});



app.use(bodyParser.urlencoded({limit: '10mb', extended: true}))
app.use(bodyParser.json({limit: '50mb', extended: true}))



app.listen(5000, function() { 
    console.log('server running on port 5000'); 
} ) 

app.post('/hr',(req,res)=>{
    
    const inputJSON = req.body;
    
    
    con.getConnection((err, connection) => {
    if(err) throw err;
        
    const username= inputJSON.username;
    const dataset = inputJSON["activities-heart-intraday"].dataset;
    const date_recorded=inputJSON["activities-heart"][0].dateTime;
    for(var i=0;i<dataset.length;i++){
    
    let updateQuery = 'UPDATE ?? SET ?? = ? WHERE ?? = ? AND ?? = ? AND ?? = ?';
    let query = mysql.format(updateQuery,["XXXX","final_heart_rate",dataset[i].value,"username",username,"date_recorded",date_recorded,"time_recorded",dataset[i].time]);
          
      connection.query(query,(err, response) => {
          if(err) {
              console.error(err);
              return;
          }
          
      
          
      });
    }
    connection.release();    
    
        
        
  });
    
    
    res.send("success"); 
})

app.get('/number', callName); 


function callName(req, res) {

    const age = req.query.age;
    const gender = req.query.gender;
    const activity = req.query.activity;
    const ihr = req.query.ihr;
    const user_speed = req.query.user_speed;
    const user_direction = req.query.user_direction;
    const wind_speed = req.query.wind_speed;
    const wind_direction = req.query.wind_direction;
    const time_recorded = req.query.time_recorded;
    const username = req.query.username;
    const date_recorded=req.query.date_recorded;


  con.getConnection((err, connection) => {
    if(err) throw err;
    
    let selectQuery = 'SELECT 1 FROM ?? WHERE ?? = ? AND ?? = ? AND ?? = ?';    
    let selQuery = mysql.format(selectQuery,["XXXX","username", username,"date_recorded",date_recorded,"time_recorded",time_recorded+":00"]);
    connection.query(selQuery,(err, data) => {
        if(err) {
            console.error(err);
            return;
        }
        if (!data.length > 0){
          let insertQuery = 'INSERT INTO ?? (??,??,??,??,??,??,??,??,??,??,??,??) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)';
          let query = mysql.format(insertQuery,["XXX","age","gender","activity","wind_speed","wind_direction","user_speed","user_direction","initial_heart_rate",
          "final_heart_rate","username","date_recorded","time_recorded"
          ,age, gender, activity,wind_speed, wind_direction, user_speed,
          user_direction, ihr, ihr, username, req.query.date_recorded, time_recorded+":00"]);
          
          connection.query(query,(err, response) => {
          if(err) {
              console.error(err);
              return;
          }
          
          
          });
        
        }
    }); 
    
      connection.release();  
        
  });


    var spawn = require("child_process").spawn; 
    var process = spawn('python3',["./dbproject.py", 
                            age,wind_speed,wind_direction,user_speed,user_direction,gender,activity,ihr
                        ] ); 

    process.stdout.on('data', function(data) { 
        res.send(data.toString()); 
    }) 
} 
