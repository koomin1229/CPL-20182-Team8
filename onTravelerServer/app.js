var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var mongoose = require('mongoose');
const bodyParser = require('body-parser');
const passport = require('passport');
var config = require('./config');


/* set routing */
var indexRouter = require('./routes/index');

var authRouter = require('./routes/auth');

var buddyRouter = require('./routes/buddies');
var checklistRouter = require('./routes/checklists');
var customerRouter = require('./routes/customers');
var feed_itemRouter = require('./routes/feed_items');
var schedule_itemRouter = require('./routes/schedule_items');
var imageRouter = require('./routes/images');
var postScriptRouter = require('./routes/post_scripts');
var favoriteBuddyRouter =require('./routes/favorite_buddies');
var favoriteFeedRouter = require('./routes/favorite_feeds');
var chattingRouter = require('./routes/chattings');
var profileRouter = require('./routes/profiles');


var app = express();

var mongo_server_url = config.mongo_url;



// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use(bodyParser.json({limit : '50mb'}));
app.use(bodyParser.urlencoded({extended: false, limit: '50mb'}));

app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'uploads')));
app.use(express.static(path.join(__dirname, 'models')));
//app.use(express.static(__dirname));

require('./passport')(passport);
app.use(passport.initialize());
app.use(passport.session());

/*  use routing   */
app.use('/', indexRouter);

app.use('/auth', authRouter);

app.use('/images', imageRouter);
app.use('/buddies', buddyRouter);
app.use('/checklists', checklistRouter);
app.use('/customers', customerRouter);
app.use('/feed_items', feed_itemRouter);
app.use('/schedule_items', schedule_itemRouter);
app.use('/post_scripts', postScriptRouter);
app.use('/favorite_buddies', favoriteBuddyRouter);
app.use('/favorite_feeds', favoriteFeedRouter);
app.use('/chattings', chattingRouter);
app.use('/profiles', profileRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});


mongoose.set('useCreateIndex', true);
mongoose.connect(mongo_server_url, {useNewUrlParser: true});

var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
  console.log("Connected to mongod server");
});


module.exports = app;
