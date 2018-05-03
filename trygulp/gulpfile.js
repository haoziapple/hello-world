var gulp = require('gulp');

gulp.task('default', function() {
	
});

gulp.task('try', function () {
	// return与否好像没区别
	gulp.src('./*.js')
		.pipe(gulp.dest('build'));
});