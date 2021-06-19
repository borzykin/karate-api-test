function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  var config = {
    env: env,
    myVarName: 'someValue'
  }
  if (env === 'dev') {
    // customize
    // e.g. config.foo = 'bar';
    config.baseUrl = 'https://reqres.in/'
    config.baseEmail = 'user@dev.domain.com'
    config.basePassword = 'Test123456'
  } else if (env === 'uat') {
    config.baseEmail = 'user@uat.domain.com'
    config.basePassword = 'Qa123456'
  }
  return config;
}