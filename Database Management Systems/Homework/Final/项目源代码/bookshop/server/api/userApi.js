var models = require('../db');
var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var $sql = require('../sqlMap');

// 连接数据库
var conn = mysql.createConnection(models.mysql);

conn.connect();

var jsonWrite = function(res, ret) {
    if(typeof ret === 'undefined') {
        res.json({
            code: '1',
            msg: '操作失败'
        });
    } else {
        res.json(ret);
    }
};

// 增加用户接口
router.post('/addUser', (req, res) => {
    var sql = $sql.user.add;
    var params = req.body;
    console.log(params);
    conn.query(sql, [params.userId, params.password], function(err, result) {
        if (err) {
            console.log(err);
        }
        if (result) {
            jsonWrite(res, result);
        }
    })
});

//查找用户接口
router.post('/selectUser', (req,res) => {
  var sql_name = $sql.user.select_name;
  var sql_password = $sql.user.select_password;
  var params = req.body;
  conn.query(sql_name,params.userId,function(err, result) {
      if(err) {
          console.log(err)
      }
      if(result[0]===undefined) {
          res.send('-1')    //查询不出username，data返回-1
      }else {
        if(params.password==result[0].password){
          jsonWrite(res, result);
        }else{
          res.send('0')//username正确后，password错误，data返回 0
        }
      }
  })
});

//查找用户详情接口
router.post('/selectUserInfo', (req,res) => {
  var sql_name = $sql.user.select_name;
  var params = req.body;
  conn.query(sql_name,params.userId,function(err, result) {
      if(err) {
          console.log(err)
      }
      if(result[0]===undefined) {
          res.send('-1')    //查询不出username，data返回-1
      }else {
        jsonWrite(res, result);
      }
  })
});

//充值余额接口
router.post('/updateExtral', (req, res) => {
    var sql = $sql.user.updateExtral;
    var params = req.body;
    console.log(params);
    conn.query(sql, [params.extral,params.userId], function(err, result) {
        if (err) {
            console.log(err);
        }
        if (result) {
            jsonWrite(res, result);
        }
    })
});

//修改用户信息接口
router.post('/updateUser', (req, res) => {
  var sql = $sql.user.updateUser;
  var params = req.body;
  console.log(params);
  conn.query(sql, [params.name,params.address,params.password,params.userId], function(err, result) {
      if (err) {
          console.log(err);
      }
      if (result) {
          jsonWrite(res, result);
      }
  })
});

//管理员修改用户信息接口
router.post('/AdminUpdate', (req, res) => {
  var sql = $sql.user.Admin_Update;
  var params = req.body;
  console.log(params);
  conn.query(sql, [params.name,params.address,params.extral,params.level,params.userId], function(err, result) {
      if (err) {
          console.log(err);
      }
      if (result) {
          console.log('更新成功')
          jsonWrite(res, result);
      }
  })
});
//查询所有用户列表
router.get('/userList', (req, res) => {
  var sql = $sql.user.select_users;
  conn.query(sql, function(err, result) {
      if (err) {
          console.log(err);
      }
      if (result) {
          jsonWrite(res, result);
      }
  })
});

router.post('/deleteuser', (req, res) => {
  var sql = $sql.user.deleteUser;
  var params = req.body;
  console.log(params);
  conn.query(sql, params.userId, function(err, result) {
      if (err) {
          console.log(err);
      }
      if (result) {
          jsonWrite(res, result);
      }
  })
});
router.post('/searchUser', (req, res) => {
  var sql = $sql.user.searchById;
  var sqlByName = $sql.user.searchByName;
  var sqlByAddress = $sql.user.searchByAddress;
  var params = req.body;
  console.log(params);
  conn.query(sql, params.factor, function(err, result) {
      if (err) {
          console.log(err);
      }
      if(result[0]===undefined){
        conn.query(sqlByName, params.factor, function(err, result){
            if (err) {
              console.log(err);
          }
          if (result[0]===undefined) {
            conn.query(sqlByAddress, params.factor, function(err, result){
              if (err) {
                console.log(err);
              }
              if(result[0]===undefined){
                res.send('-1')
              }else{
                jsonWrite(res, result);
              }
          })
          }else{
            jsonWrite(res, result);
          }
        })
      }else{
        jsonWrite(res, result);
      }
  })
});
module.exports = router;
