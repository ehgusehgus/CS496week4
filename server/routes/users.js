var express = require('express');
var router = express.Router();
var db = require('../database');


const asyncMiddleware = fn => (req, res, next) => {
  Promise.resolve(fn(req, res, next))
    .catch(next);
  }

/* GET users listing. */
router.get('/', asyncMiddleware(async function(req, res, next) {
  var facebook_id = req.headers.facebook_id;
  res.json(await db.getUserData(facebook_id))
}));

router.post('/create', asyncMiddleware(async function(req, res, next) {
  var facebook_id = req.body.facebook_id
  var nickname = req.body.nickname
  res.json(await db.postUserData(facebook_id, nickname));
}));

router.get('/interest', asyncMiddleware(async function(req, res, next) {
  var facebook_id = req.headers.facebook_id;
  res.json(await db.getInterest(facebook_id))
}))


module.exports = router;
