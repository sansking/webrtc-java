/*
 * Copyright (c) 2019, Alex Andres. All rights reserved.
 *
 * Use of this source code is governed by the 3-Clause BSD license that can be
 * found in the LICENSE file in the root of the source tree.
 */

#include "JavaArray.h"
#include "JavaBigInteger.h"
#include "JavaClasses.h"
#include "JavaObject.h"
#include "JavaString.h"
#include "JavaUtils.h"

namespace jni
{
	JavaBigInteger::JavaBigInteger(JNIEnv * env)
	{
		cls = FindClass(env, "java/math/BigInteger");

		ctor = GetMethod(env, cls, "<init>", "(Ljava/lang/String;)V");
	}

	JavaLocalRef<jobject> JavaBigInteger::toJava(JNIEnv * env, const std::string & val)
	{
		const auto javaClass = JavaClasses::get<JavaBigInteger>(env);

		jobject object = env->NewObject(javaClass->cls, javaClass->ctor, JavaString::toJava(env, val).get());

		return JavaLocalRef<jobject>(env, object);
	}

	JavaLocalRef<jobjectArray> JavaBigInteger::createArray(JNIEnv * env, const std::vector<std::string> & vector)
	{
		const auto javaClass = JavaClasses::get<JavaBigInteger>(env);

		return JavaArray::createObjectArray(env, vector, javaClass->cls, &toJava);
	}
}