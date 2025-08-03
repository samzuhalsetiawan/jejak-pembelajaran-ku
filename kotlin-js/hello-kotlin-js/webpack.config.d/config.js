config.module.rules.find(rule => rule.test.test(".css"))?.use.push(
    {
        loader: 'style-loader',
        options: {}
    },
    {
        loader: 'postcss-loader',
        options: {}
    }
)