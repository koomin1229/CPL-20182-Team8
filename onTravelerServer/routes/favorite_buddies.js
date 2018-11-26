var express = require('express');
var router = express.Router();
var Favorite_buddy = require('../models/favorite_buddy');
var Buddy = require('../models/buddy');
var ReturnFormat = require('../return_form')();

/* GET Favorite_buddy listing. */
router.get('/', function(req, res, next) {
    Favorite_buddy.findAll()
        .then(function(favorite_buddy){
            if(!favorite_buddy.length) return res.status(404).send(ReturnFormat.get_format(0, favorite_buddy, "not_found"));
            res.send(ReturnFormat.get_format(1, favorite_buddy, ""));
        })
        .catch(function(err){
            res.status(500).send(ReturnFormat.get_format(0, "", err));
        });
});






/* Get particular favorite_buddies*/
router.get('/customer/:customer_id', function(req, res, next){
    Favorite_buddy.find({customer_id : req.params.customer_id})
        .then(function(favorite_buddy){
            if(!favorite_buddy.length) return res.status(404).send(ReturnFormat.get_format(0, favorite_buddy, "not_found"));
            res.send(ReturnFormat.get_format(1, favorite_buddy, ""));
        })
        .catch(function(err){
            res.status(500).send(ReturnFormat.get_format(0, "", err));
        });
});


router.get('/buddy/:buddy_id', function(req, res, next){
    Favorite_buddy.find({buddy_id : req.params.buddy_id})
        .then(function(favorite_buddy){
            if(!favorite_buddy.length) return res.status(404).send(ReturnFormat.get_format(0, favorite_buddy, "not_found"));
            res.send(ReturnFormat.get_format(1, favorite_buddy, ""));
        })
        .catch(function(err){
            res.status(500).send(ReturnFormat.get_format(0, "", err));
        });
});



/* Get particular favorite_buddies*/
router.get('/buddies_info/:customer_id', function(req, res, next){
    Favorite_buddy.find({customer_id : req.params.customer_id})
        .then(function(favorite_buddy){
            if(!favorite_buddy.length) return res.status(404).send(ReturnFormat.get_format(0, favorite_buddy, "not_found"));

            var buddy_id_list = [];
            for(var i=0; i<favorite_buddy.length; i++){
                buddy_id_list[i] = favorite_buddy[i].buddy_id;
            }

            Buddy.find({buddy_id: {$in : buddy_id_list}})
                .then(function (buddy) {
                    if(!buddy.length) return res.status(404).send(ReturnFormat.get_format(0, buddy, "not_found"));
                    res.send(ReturnFormat.get_format(1, buddy, ""));
                });
        })
        .catch(function(err){
            res.status(500).send(ReturnFormat.get_format(0, "", err));
        });
});



/* Create favorite_buddy or update */
router.post('/', function(req, res){
    Favorite_buddy.create(req.body)
        .then(function(favorite_buddy){
            res.send(ReturnFormat.post_format(1, [favorite_buddy], ""));
        }).catch(function(err){
        res.status(500).send(ReturnFormat.get_format(0, "", err));
    });
});

router.put('/:favorite_buddy_id', function (req, res) {
    Favorite_buddy.updateMany({_id : req.params.favorite_buddy_id}, {$set:req.body})
        .then(function(favorite_buddy){
            res.send(ReturnFormat.put_format(1, favorite_buddy, ""));
        }) .catch(function(err){
        res.status(500).send(ReturnFormat.put_format(0,"", err));
    });
});



/* Delete favorite_buddy */
router.delete('/:favorite_buddy_id', function(req, res){
    Favorite_buddy.remove({_id: req.params.favorite_buddy_id})
        .then(function(){
            res.send(ReturnFormat.delete_format(1,""));
        }).catch(function(err){
        res.status(500).send(ReturnFormat.delete_format(0, err));
    });
});

module.exports = router;
