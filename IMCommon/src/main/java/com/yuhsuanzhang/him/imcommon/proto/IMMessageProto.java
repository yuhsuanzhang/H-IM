// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/java/com/yuhsuanzhang/him/imcommon/proto/IMMessage.proto

package com.yuhsuanzhang.him.imcommon.proto;

public final class IMMessageProto {
  private IMMessageProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface IMMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.yuhsuanzhang.him.imcommon.proto.IMMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string channelId = 1;</code>
     * @return The channelId.
     */
    java.lang.String getChannelId();
    /**
     * <code>string channelId = 1;</code>
     * @return The bytes for channelId.
     */
    com.google.protobuf.ByteString
        getChannelIdBytes();

    /**
     * <code>string message = 2;</code>
     * @return The message.
     */
    java.lang.String getMessage();
    /**
     * <code>string message = 2;</code>
     * @return The bytes for message.
     */
    com.google.protobuf.ByteString
        getMessageBytes();
  }
  /**
   * Protobuf type {@code com.yuhsuanzhang.him.imcommon.proto.IMMessage}
   */
  public static final class IMMessage extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.yuhsuanzhang.him.imcommon.proto.IMMessage)
      IMMessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use IMMessage.newBuilder() to construct.
    private IMMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private IMMessage() {
      channelId_ = "";
      message_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new IMMessage();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage.class, com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage.Builder.class);
    }

    public static final int CHANNELID_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile java.lang.Object channelId_ = "";
    /**
     * <code>string channelId = 1;</code>
     * @return The channelId.
     */
    @java.lang.Override
    public java.lang.String getChannelId() {
      java.lang.Object ref = channelId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        channelId_ = s;
        return s;
      }
    }
    /**
     * <code>string channelId = 1;</code>
     * @return The bytes for channelId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getChannelIdBytes() {
      java.lang.Object ref = channelId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        channelId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    @SuppressWarnings("serial")
    private volatile java.lang.Object message_ = "";
    /**
     * <code>string message = 2;</code>
     * @return The message.
     */
    @java.lang.Override
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      }
    }
    /**
     * <code>string message = 2;</code>
     * @return The bytes for message.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(channelId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, channelId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(channelId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, channelId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage)) {
        return super.equals(obj);
      }
      com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage other = (com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage) obj;

      if (!getChannelId()
          .equals(other.getChannelId())) return false;
      if (!getMessage()
          .equals(other.getMessage())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CHANNELID_FIELD_NUMBER;
      hash = (53 * hash) + getChannelId().hashCode();
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.yuhsuanzhang.him.imcommon.proto.IMMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.yuhsuanzhang.him.imcommon.proto.IMMessage)
        com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage.class, com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage.Builder.class);
      }

      // Construct using com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        channelId_ = "";
        message_ = "";
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_descriptor;
      }

      @java.lang.Override
      public com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage getDefaultInstanceForType() {
        return com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage.getDefaultInstance();
      }

      @java.lang.Override
      public com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage build() {
        com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage buildPartial() {
        com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage result = new com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.channelId_ = channelId_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.message_ = message_;
        }
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage) {
          return mergeFrom((com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage other) {
        if (other == com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage.getDefaultInstance()) return this;
        if (!other.getChannelId().isEmpty()) {
          channelId_ = other.channelId_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (!other.getMessage().isEmpty()) {
          message_ = other.message_;
          bitField0_ |= 0x00000002;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                channelId_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 18: {
                message_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private java.lang.Object channelId_ = "";
      /**
       * <code>string channelId = 1;</code>
       * @return The channelId.
       */
      public java.lang.String getChannelId() {
        java.lang.Object ref = channelId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          channelId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string channelId = 1;</code>
       * @return The bytes for channelId.
       */
      public com.google.protobuf.ByteString
          getChannelIdBytes() {
        java.lang.Object ref = channelId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          channelId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string channelId = 1;</code>
       * @param value The channelId to set.
       * @return This builder for chaining.
       */
      public Builder setChannelId(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        channelId_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>string channelId = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearChannelId() {
        channelId_ = getDefaultInstance().getChannelId();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>string channelId = 1;</code>
       * @param value The bytes for channelId to set.
       * @return This builder for chaining.
       */
      public Builder setChannelIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        channelId_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private java.lang.Object message_ = "";
      /**
       * <code>string message = 2;</code>
       * @return The message.
       */
      public java.lang.String getMessage() {
        java.lang.Object ref = message_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          message_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string message = 2;</code>
       * @return The bytes for message.
       */
      public com.google.protobuf.ByteString
          getMessageBytes() {
        java.lang.Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string message = 2;</code>
       * @param value The message to set.
       * @return This builder for chaining.
       */
      public Builder setMessage(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        message_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>string message = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessage() {
        message_ = getDefaultInstance().getMessage();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <code>string message = 2;</code>
       * @param value The bytes for message to set.
       * @return This builder for chaining.
       */
      public Builder setMessageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        message_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.yuhsuanzhang.him.imcommon.proto.IMMessage)
    }

    // @@protoc_insertion_point(class_scope:com.yuhsuanzhang.him.imcommon.proto.IMMessage)
    private static final com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage();
    }

    public static com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<IMMessage>
        PARSER = new com.google.protobuf.AbstractParser<IMMessage>() {
      @java.lang.Override
      public IMMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<IMMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<IMMessage> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.yuhsuanzhang.him.imcommon.proto.IMMessageProto.IMMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\nAsrc/main/java/com/yuhsuanzhang/him/imc" +
      "ommon/proto/IMMessage.proto\022#com.yuhsuan" +
      "zhang.him.imcommon.proto\"/\n\tIMMessage\022\021\n" +
      "\tchannelId\030\001 \001(\t\022\017\n\007message\030\002 \001(\tB5\n#com" +
      ".yuhsuanzhang.him.imcommon.protoB\016IMMess" +
      "ageProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yuhsuanzhang_him_imcommon_proto_IMMessage_descriptor,
        new java.lang.String[] { "ChannelId", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}