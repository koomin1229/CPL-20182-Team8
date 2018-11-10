var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var buddySchema = new Schema({
    buddy_id : { type : String, required : true , unique: true},
    pw : { type : String, required : true , trim : true},
    name : { type : String, required : true },
    active_location : String,
    notification : String,
    price_list : [
        {
            name : {type:String, required : true},
            money : {type : Number, required : true, min : 0},
        }
    ]
});


buddySchema.statics.create = function(data){
    var instance = new this(data);

    return instance.save();
};


buddySchema.statics.findAll = function(){
    return this.find({});
};


module.exports = mongoose.model('buddy', buddySchema);