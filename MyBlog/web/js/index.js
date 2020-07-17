function onLoad(ev) {
    // 有用户点击 button, 发起 ajax 请求
    let xhr = new XMLHttpRequest();
    xhr.open("get", "/api/article_list.json");
    xhr.onreadystatechange = function(ev) {
        if(xhr.readyState == XMLHttpRequest.DONE) { // ajax 请求完成时
            if(xhr.status == 200) {                 // HTTP 响应 200 的时候
                let articleList = JSON.parse(xhr.responseText); // 把结果按照 JSON 格式解析
                console.log(articleList);

                // 把得到的结果放入 html 中
                let articleListElement = document.querySelector("#article_list");  // html 输出的标签
                if(articleList.length > 0) {
                    articleListElement.innerHTML = "";
                    for (let i in articleList) {
                        let article = articleList[i];
                        let html = "<li>" +
                            // 这个中括号的用法, 就类似于 java 中 map.get("id"); 的用法一样
                            "<a href= \"/article?id=" + article["id"] + "\">"
                            + article["title"] +
                            "</a>" + article["published_at"] + "</li>";
                        console.log(html);
                        articleListElement.innerHTML += html;  // 把拼接好的 html, 放入标签中
                    }
                }
            }
        }
    };
    xhr.send();
}

window.onLoad(onLoad);