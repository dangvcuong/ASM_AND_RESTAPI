const mongoose = require('mongoose')
const Sheme = mongoose.Schema;

const ShoseHeart = new Sheme({
    id_User_heart:{type:String},
    id_Shose_heart:{type:String},
    shose_Name_heart:{type:String},
    description_heart:{type:String},
    price_heart:{type:Number},
    image_anh_heart:{type:String},
},{
    timestamps:true
})
module.exports = mongoose.model('shoseheart',ShoseHeart)