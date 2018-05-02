var gulp = require('gulp');

gulp.task('default', function() {
	
});

gulp.task('try', function () {
	
	// 不return的话会多出来一个output文件夹
	return gulp.src('./*.js')
		.pipe(gulp.dest('build'));
});