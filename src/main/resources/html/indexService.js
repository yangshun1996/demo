function queryAllProcess() {
    $.ajax({
        type: "POST",
        url: "http://localhost:9999/act/queryAllProcess",
        dataType: "json",
        success: function(data){
            var tableBody = "   <thead>\n" +
                "    <tr>\n" +
                "        <th scope=\"col\">流程id&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n" +
                "        <th scope=\"col\">流程名称</th>\n" +
                "    </tr>\n" +
                "    </thead>";


            for ( var i = 0 ; i < data.length ; i ++){
                var id = data[i].KEY_;
                var name = data[i].DGRM_RESOURCE_NAME_;
                var useId = "'" + data[i].ID_ +"'";
                var useKey = "'" + data[i].KEY_ +"'";
                tableBody = tableBody + "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <th scope=\"col\">" + id + "&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n" +
                    "        <th scope=\"col\">" + name + "&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n" +
                    "         <th scope=\"col\" onclick=\"queryProcess(" + useId + ")\">查看流程</th>" +
                    "         <th scope=\"col\" onclick=\"startProcess(" + useKey + " , " + useId + ")\">启动流程</th>" +
                    "    </tr>\n" +
                    "    </tbody>"
            }
            tableBody = tableBody + "</table>";
            $('#processTable').html(tableBody);
        },
        error: function (data) {
            alert('加载失败');
        },
    });
}




function startProcess(ref , ref2) {
    $.ajax({
        type: "POST",
        data: {processId:ref},
        url: "http://localhost:9999/act/startProcess",
        dataType: "json",
        success: function(data){
            alert(data)
        },
        error: function (data) {
            queryProcess(ref2);
        }
    });

}

function queryProcess(ref) {
    $.ajax({
        type: "POST",
        data: {processId:ref},
        url: "http://localhost:9999/act/queryTasksByProcessId",
        dataType: "json",
        success: function(data){
            var tableBody = "   <thead>\n" +
                "    <tr>\n" +
                "        <th scope=\"col\">任务id&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n" +
                "        <th scope=\"col\">执行ID</th>\n" +
                "        <th scope=\"col\">任务名称</th>\n" +

                "    </tr>\n" +
                "    </thead>";


            for ( var i = 0 ; i < data.length ; i ++){
                var id = data[i].ID_;
                var name = data[i].TASK_DEF_KEY_;
                var EXECUTION_ID_ = data[i].EXECUTION_ID_;
                var useId = "'" + data[i].ID_ +"'";
                var useId2 = "'" + data[i].PROC_DEF_ID_ +"'";
                var useEXECUTION_ID_ ="'" + data[i].EXECUTION_ID_ +"'";
                tableBody = tableBody + "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <th scope=\"col\">" + id + "&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n" +
                    "        <th scope=\"col\">" + EXECUTION_ID_ + "&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n" +
                    "        <th scope=\"col\">" + name + "&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n" +
                    "         <th scope=\"col\" onclick=\"completeTask(" + useId + " , " + useId2 + ")\">完成任务</th>" +
                    "         <th scope=\"col\" onclick=\"queryHistory(" + useEXECUTION_ID_ +")\">查看历史</th>" +
                    "    </tr>\n" +
                    "    </tbody>"
            }
            tableBody = tableBody + "</table>";
            $('#taskTable').html(tableBody);
        },
        error: function (data) {
            alert('加载失败');
        },
    });
}

function completeTask(ref ,ref2) {
    $.ajax({
        type: "POST",
        data: {taskId:ref},
        url: "http://localhost:9999/act/completePersonalTask",
        dataType: "json",
        error: function(data){
            queryProcess(ref2);
        },
    });
}

function queryHistory(ref) {
    $.ajax({
        type: "POST",
        data: {processId:ref},
        url: "http://localhost:9999/act/findHistoryProcessInstance",
        dataType: "json",
        error: function(data){
            alert(data.responseText);
            console.log(data);
        },
    });
}

function queryTask() {
    var assignee= $("#assignee").val();
    $.ajax({
        type: "POST",
        data: {assignee:assignee},
        url: "http://localhost:9999/act/queryTask",
        dataType: "json",
        error: function(data){
            alert(data.responseText);
            console.log(data);
        },
    });
}
