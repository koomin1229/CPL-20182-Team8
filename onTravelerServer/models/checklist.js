var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var checkListSchema = new Schema({
    customer_id : {type:String, required: true},
    buddy_id : {type:String, required: true},
    customer_name : String,
    buddy_name : String,
    state : { type:String, required: true},
    start_year :String,
    start_month : String,
    start_day : String,
    end_year : String,
    end_month : String,
    end_day :String,
    location_name : String,
    key: String,
    people_number : {type : Number, min : 1, required:true},
    requirement_list : [String],
    suggested_price : {type : Number, min : 0, required: true}
});

checkListSchema.statics.create = function(data){
    var instance = new this(data);

    return instance.save();
};


checkListSchema.statics.findAll = function(){
    return this.find({});
};

module.exports = mongoose.model('checkList', checkListSchema);