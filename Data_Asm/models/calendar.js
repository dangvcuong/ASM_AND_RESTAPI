const mongoose = require('mongoose')
const Sheme = mongoose.Schema;

const Calendar = new Sheme({
    id_User_Calendar: { type: String },
    id_Shose_Calendar: { type: String },
    id_cart_Calendar: { type: String },
    ten_shose_Calendar: { type: String },
    gia_shose_Calendar: { type: Number },
    hinh_anh_Calendar: { type: String },
    date_Calendar: { type: String }
}, {
    timestamps: true
})
module.exports = mongoose.model('canlendar', Calendar)