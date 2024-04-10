const mongoose = require('mongoose')
const Sheme = mongoose.Schema;

const Shoses = new Sheme({
    shoseName:{type:String},
    description:{type:String},
    price:{type:Number},
    image_anh:{type:String},
},{
    timestamps:true
})
module.exports = mongoose.model('shose',Shoses)