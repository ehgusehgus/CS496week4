var mysql_dbc = require('./db/db_con')();
var connection = mysql_dbc.init();
mysql_dbc.test_open(connection);

let getUserData = (facebook_id)   => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT * FROM user WHERE facebook_id=' + facebook_id + ';'
    connection.query(q, function(err, result){
      if(err){
        console.error(err)
      } else {
        if(result.length == 0) {
          resolve({"messages" : "None"})
        } else {
          resolve(result[0])
        }
      }
    })
  })
}

exports.getUserData = async function getUser(facebook_id) {
  var result;
  await getUserData(facebook_id).then(function(data) {
    result = data;
  })
  return {'result' : result};
}

let postUserData = (facebook_id, nickname) => {
  return new Promise((resolve, reject) => {
    var q = 'insert into user values(\"' + facebook_id + '\", \"' + nickname +'\", ' + 'now())'
    connection.query(q, function(err, result) {
      if(err){
        console.error(err);
        resolve({"Messages" : "ERROR" });
      } else {
        console.log("Create Success");
        resolve({"Messages" : "GOOD" });
      }
    })
  })
};

exports.postUserData = async function postUser(facebook_id, nickname) {
  var result;
  await postUserData(facebook_id, nickname).then(function(data) {
    result = data;
  })
  return {'result' : result };
}

// Get Content And Recipe
let getContentData = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT content.keyword as keyword, tag, ingredient, category_con, category_cooking, creater, content.created_at as created_at, updated_at, nickname FROM content, user, tag WHERE content.keyword=\"' + keyword + '\" AND creater = facebook_id AND content.keyword=tag.keyword;'
    connection.query(q, function(err, result){
      if(err) {
        console.error(err);
      } else {
        resolve(result[0] );
      }
    });
  });
};

let getRecipeData = (keyword) => {
  return new Promise((resolve, reject) => {
    q = 'SELECT index_no, descript, editer, recipe.created_at as created_at, updated_at, nickname FROM recipe, user WHERE keyword=\"' + keyword + '\" AND editer=facebook_id;'
    connection.query(q, function(err, result){
      if(err){
        console.error(err);
      } else {
        resolve(result);
      }
    })
  });
};

let getTagData = (keyword) => {
  return new Promise( (resolve, reject) =>{
    var q = 'SELECT tag FROM tag WHERE keyword = \"' + keyword + '\";'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}



exports.getContentAndRecipes = async function getContentAndRecipes(keyword){
  var content, recipes, tags;
  await getContentData(keyword).then(function(data){
    content = data;
  });
  await getRecipeData(keyword).then(function(data){
    recipes = data;
  });
  await getTagData(keyword).then(function(data) {
    tags = data;
  })
  return {content, recipes, 'tags': tags}
}


// Post Content And Recipe
let postContentData = (table, keyword, ingredient, creater, category_con, category_cooking) => {
  return new Promise((resolve ,reject) => {
    var q = 'INSERT INTO '+ table + ' values(\"' + keyword + '\", \"' + ingredient +'\", \"' + category_con + '\", \"' + category_cooking + '\", \"' + creater + '\", now(), now());'
    connection.query(q, function(err, result){
      if(err) {
        console.error(err);
      } else {
        console.log("Create Content Successfully");
        resolve();
      }
    })
  })
}

let postRecipeData = (table, keyword, creater, recipe) => {
  return new Promise((resolve, reject) => {
    var q = 'INSERT INTO '+ table +' values(\"' + keyword + '\", \"' + recipe.index + '\", \"' + recipe.descript + '\", \"' + creater + '\", now(), now())'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        console.log("Create Recipe Successfully");
        resolve();
      }
    })
  })
}

let postTagData = (table, keyword, tag) => {
  return new Promise((resolve, reject) => {
    var q = 'INSERT INTO ' + table + ' values(\"' + keyword + '\", \"' + tag + '\");'
    connection.query(q, function(err, result) {
      if(err){
        console.error(err);
      } else {
        console.log("Create tag Successfully");
        resolve();
      }
    })
  })
}

exports.postContentAndRecipes = async function postContentAndRecipes(flag, keyword, ingredient, creater, category_con, category_cooking, recipes, tags){
  var table_c = "content", table_r = "recipe", table_t = "tag";
  if(flag == true) {
    table_c += "_edit"
    table_r += "_edit"
    table_t += "_edit"
  }
  await postContentData(table_c, keyword, ingredient, creater, category_con, category_cooking);
  for(var i in recipes){
    await postRecipeData(table_r, keyword, creater, recipes[i]);
  }

  for(var i in tags){
    if(tags[i] == "" ){
      continue;
    }
    await postTagData(table_t, keyword, tags[i]);
  }
  console.log("Create Successfully");
  return {"Messages" : "FINISH"}
}

let getRandomContent = (count) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT keyword, nickname FROM content, user WHERE creater=facebook_id ORDER BY RAND() LIMIT ' + count + ';'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

let getLatestUpdatedContent = (count) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT keyword, nickname FROM content, user WHERE creater=facebook_id ORDER BY updated_at DESC LIMIT ' + count + ';'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

let getMostInterestContent = (count) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT content.keyword  FROM content LEFT OUTER JOIN interest ON content.keyword=interest.keyword GROUP BY content.keyword ORDER BY count(user_id) DESC LIMIT ' + count + ';';
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

exports.getContentInMain = async function getRandomContent_(count) {
  var random, latest, mostInterest;
  await getRandomContent(count).then(function(data) {
    random = data;
  })
  await getLatestUpdatedContent(count).then(function(data) {
    latest = data;
  })
  await getMostInterestContent(count).then(function(data) {
    mostInterest = data;
  })
  return { 'random' : random, 'latest' : latest, 'mostInterest' : mostInterest }
}

let getCountryCategoryContent = (category) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT keyword FROM content WHERE category_con=\"' + category + '\";';
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}


exports.getCountryCategoryContent = async function getCountryCategoryContent_(category) {
  var result;
  await getCountryCategoryContent(category).then(function(data) {
    result = data
  })
  return {'result' : result}
}


let getCookingCategoryContent = (category) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT keyword FROM content WHERE category_cooking=\"' + category + '\";';
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

exports.getCookingCategoryContent = async function getCookingCategoryContent_(category) {
  var result;
  await getCookingCategoryContent(category).then(function(data) {
    result = data
  })
  return {'result' : result}
}



let onInterest = (keyword, facebook_id) => {
  return new Promise((resolve, reject) => {
    var q = 'INSERT INTO interest values(\"' + facebook_id + '\", \"' + keyword + '\", now());'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve();
      }
    })
  })
}

exports.onInterest = async function onInterest_(keyword, facebook_id) {
  await onInterest(keyword, facebook_id);
  return {'messages' : 'SUCCESS'};
}

let offInterest = (keyword, facebook_id) => {
  return new Promise((resolve, reject) => {
    var q = 'DELETE FROM interest WHERE keyword=\"'+ keyword + '\" AND user_id=\"' + facebook_id + '\";'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve();
      }
    })
  })
}

exports.offInterest = async function offInterest_(keyword, facebook_id) {
  await offInterest(keyword, facebook_id);
  return {'messages' : 'SUCCESS'};
}


let getNoticeList = () => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT keyword, created_at FROM content_edit'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err)
      } else {
        resolve(result);
      }
    })
  })
}

exports.getNoticeList = async function getNoticeList_() {
  var result;
  await getNoticeList().then(function(data) {
    result = data;
  });
  return {'result': result};
}

let getNoticeContent = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT content.keyword as keyword, ingredient, category_con, category_cooking, creater, content.created_at as created_at, updated_at, nickname FROM content, user WHERE content.keyword=\"' + keyword + '\" AND creater = facebook_id;'
    connection.query(q, function(err, result) {
      if(err){
        console.error(err)
      } else {
        resolve(result[0]);
      }
    })
  })
}

let getNoticeEditContent = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT content_edit.keyword as keyword, ingredient, category_con, category_cooking, creater, content_edit.created_at as created_at, updated_at, nickname FROM content_edit, user WHERE content_edit.keyword=\"' + keyword + '\" AND creater = facebook_id;'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result[0]);
      }
    })
  })
}

let getNoticeRecipe = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT index_no, descript FROM recipe WHERE keyword=\"' + keyword + '\";'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

let getNoticeEditRecipe = (keyword) => {
  return new Promise((resolve, reject) => {
      var q = 'SELECT index_no, descript FROM recipe_edit WHERE keyword=\"' + keyword + '\";'
      connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

let getNoticeTag = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT tag FROM tag WHERE keyword=\"' + keyword + '\";'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

let getNoticeEditTag = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT tag FROM tag_edit WHERE keyword=\"' + keyword + '\";'
    connection.query(q, function(err, result) {
      if(err){
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

exports.getNoticeDetail = async function getNoticeDetail(keyword) {
  var content, content_edit, recipe, recipe_edit, tag, tag_edit;
  await getNoticeContent(keyword).then(function(data){
    content = data;
  });
  await getNoticeEditContent(keyword).then(function(data) {
    content_edit = data;
  })
  await getNoticeRecipe(keyword).then(function(data){
    recipe = data;
  })
  await getNoticeEditRecipe(keyword).then(function(data) {
    recipe_edit = data;
  })
  await getNoticeTag(keyword).then(function(data){
    tag = data;
  })
  await getNoticeEditTag(keyword).then(function(data) {
    tag_edit = data;
  })
  return { 'content' : content, 'content_edit' : content_edit, 'recipes' : recipe, 'recipes_edit' : recipe_edit, 'tag' : tag, 'tag_edit' : tag_edit }
}

let postVote = (keyword, facebook_id, agree) => {
  return new Promise((resolve, reject) => {
    var q = 'INSERT INTO vote values( \'' + keyword + '\', \'' + facebook_id + '\', \'' + agree + '\');'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve();
      }
    })
  })
}

exports.postVote = async function postVote_(keyword, facebook_id, agree) {
  await postVote(keyword, facebook_id, agree);
  return {"messages" : "success"}
}

let checkVote = (keyword, facebook_id) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT * FROM vote WHERE keyword= \"'+ keyword +  '\" AND facebook_id=\"' + facebook_id + '\"'
    connection.query(q, function(err, result) {
      if(err){
        console.error(err);
      } else {
        resolve(result.length);
      }
    })
  })
}

exports.checkVote = async function checkVote_(keyword, facebook_id) {
  var result;
  await checkVote(keyword, facebook_id).then(function(data) {
    result = {'result' : data}
  })
  return result;
}

let updateContent = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'UPDATE content as A, content_edit as B set A.ingredient = B.ingredient, A.creater = B.creater, A.category_con = B.category_con, A.category_cooking = B.category_cooking, A.updated_at = B.updated_at WHERE A.keyword = B.keyword;'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err)
      } else {
        resolve();
      }
    })
  })
}

exports.updateContent = async function updateContent_(keyword) {
  await updateContent(keyword);
  await deleteSomething(keyword, 'content_edit');
}


let getRecipeEditData = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT * FROM recipe_edit WHERE keyword=\"' + keyword + '\";'
    connection.query(q, function(err, result){
      if(err){
        console.error(err);
      } else {
        resolve(result);
      }
    })
  });
};

let postRecipeEdit = (keyword, index_no, descript, editer, created_at, updated_at) => {
  return new Promise((resolve, reject) => {
    var q = 'INSERT IGNORE INTO recipe values(\"' + keyword + '\", \"' + index_no + '\", \"' + descript + '\", \"' + editer + '\", \"' + created_at + '\", \"' + updated_at +'\");'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve();
      }
    })
  })
}

exports.updateRecipes = async function updateRecipe_(keyword) {
  var recipes_edit;
  await deleteSomething(keyword, 'recipe');
  await getRecipeEditData(keyword).then(function(data) {
    recipes_edit = data;
  })
  for(var i in recipes_edit){
    await postRecipeEdit(recipes_edit[i].keyword, recipes_edit[i].index_no, recipes_edit[i].descript, recipes_edit[i].editer, recipes_edit[i].created_at, recipes_edit[i].updated_at);
  }
  await deleteSomething(keyword, 'recipe_edit');
}

let getTagEditData = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT * FROM tag_edit WHERE keyword=\"' + keyword + '\";'
    connection.query(q, function(err, result){
      if(err){
        console.error(err);
      } else {
        resolve(result);
      }
    })
  });
};

let postTagEdit = (keyword, tag) => {
  return new Promise((resolve, reject) => {
    var q = 'INSERT INTO tag values(\"' + keyword + '\", \"' + tag + '\");'
    connection.query(q, function(err, result) {
      if(err){
        console.error(err);
      } else {
        resolve();
      }
    })
  })
}

exports.updateTags = async function updateTags_(keyword) {
  var tags_edit;
  await deleteSomething(keyword, 'tag');
  await getTagEditData(keyword).then(function(data) {
    tags_edit = data;
  });
  for(var i in tags_edit){
    await postTagEdit(tags_edit[i].keyword, tags_edit[i].tag);
  }
  await deleteSomething(keyword, 'tag_edit');
}

exports.doNotUpdate = async function doNotUpdaet_(keyword) {
  await deleteSomething(keyword, 'content_edit');
  await deleteSomething(keyword, 'recipe_edit');
  await deleteSomething(keyword, 'tag_edit');
}

let deleteSomething = (keyword, table) => {
  return new Promise((resolve, reject) => {
    var q = 'DELETE FROM ' + table  + ' WHERE keyword = \'' + keyword + '\';'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        resolve();
      }
    })
  })
}


let checkNoticeAgree = (keyword) => {
  return new Promise((resolve, reject) => {
    var numAgree, numTotal;
    var q = 'SELECT count(keyword) as count FROM vote WHERE keyword=\'' + keyword + '\' group by keyword;'
    connection.query(q, function(err, result) {
      if(err) {
        console.error(err);
      } else {
        try {
          numTotal = result[0].count;
        }
        catch(err) {
          numTotal = 0;
        }
        var q1 = 'SELECT count(agree) as count FROM vote WHERE keyword=\'' + keyword + '\' AND agree=1 group by keyword;'
        connection.query(q1, function(err,result) {
          if(err){
            console.error(err);
          } else{
            try {
              numAgree = result[0].count;
            }
            catch(err) {
              numAgree = 0;
            }
            if(numAgree >= parseInt(numTotal / 2) + 1){
              resolve(true);
            } else{
              resolve(false);
            }
          }
        })
      }
    })
  })
}

exports.checkNoticeAgree = async function checkNoticeAgree_(keyword)  {
  var data1;
  await checkNoticeAgree(keyword).then(function(data) {
    data1 = data;
  });
  await deleteSomething(keyword, 'vote');
  return data1;
}



let searchKeyword = (keyword) => {
  return new Promise((resolve, reject) => {
    var q = '(SELECT keyword FROM content WHERE keyword=\"' + keyword + '\" ) union distinct (SELECT keyword FROM tag WHERE tag=\"' + keyword + '\")';
    connection.query(q, function(err, result){
      if(err) {
        console.error(err);
      } else {
        resolve({ 'result' : result });
      }
    });
  });
}

exports.Search = async function searchKeyword_(keyword){
  var ret;
  await searchKeyword(keyword).then(function(data){
    ret = data;
  })
  return { 'result' : ret }
}


let isInterest = (keyword, facebook_id) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT * FROM interest WHERE keyword=\"' + keyword + '\" AND user_id=\"' + facebook_id + '\";'
    connection.query(q, function(err, result) {
      if(err){
        console.error(err);
      } else {
        if(result.length == 0){
          return resolve(false);
        } else{
          resolve(true)
        }
      }
    })
  })
}

exports.isInterest = async function isInterest_(keyword, facebook_id) {
  var ret;
  await isInterest(keyword,facebook_id).then(function(data){
    ret = data;
  })
  return {'result' : ret };
}


let getInterest = (facebook_id) => {
  return new Promise((resolve, reject) => {
    var q = 'SELECT keyword FROM interest WHERE user_id=\"' + facebook_id + '\";';
    connection.query(q, function(err, result) {
      if(err){
        console.error(err);
      } else {
        resolve(result);
      }
    })
  })
}

exports.getInterest = async function getInterest_(facebook_id) {
  var ret;
  await getInterest(facebook_id).then(function(data) {
    ret = data;
  })
  return {'result' : ret}
}
