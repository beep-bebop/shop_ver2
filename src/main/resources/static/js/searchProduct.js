var xhr;

function getProducts() {

    document.getElementById("productList").innerHTML="";
    var keyWords=document.getElementById("searchProductList").value;
    xhr=new XMLHttpRequest();
    xhr.open("get","../catalog/searchProducts?keyword="+keyWords,true);
    xhr.onreadystatechange=getProductList;
    xhr.send(null);
}

function getProductList() {
    if(xhr.readyState==4&&xhr.status==200)
    {
        var xmlDoc = xhr.responseXML.documentElement;
        //console.log(xmlDoc);
        var nameList=xmlDoc.getElementsByTagName("name");
        console.log(nameList);
        var idList=xmlDoc.getElementsByTagName("productId");
        var ProductList=document.getElementById("productList");
        var option=null;
        console.log(nameList.length);
        for(var i=0;i<nameList.length;i++)
        {
            option=document.createElement("option");
            option.appendChild(document.createTextNode(nameList[i].firstChild.nodeValue));
            option.setAttribute("value",nameList[i].firstChild.nodeValue);
            option.setAttribute("id",idList[i].firstChild.nodeValue);

            ProductList.appendChild(option);
        }
    }
}
function  turnTo(str) {
    console.log(str);
}