
Java.perform(function(){

let MainActivity = Java.use("com.demo.myapplication.MainActivity");
MainActivity["isRootAvailable"].implementation = function () {
    console.log(`MainActivity.isRootAvailable is called`);
    return false;
};
});
