package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.members.MemberService;
import com.alkemy.ong.domain.members.Members;
import com.alkemy.ong.domain.utils.PageModel;
import com.alkemy.ong.web.controllers.utils.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @Operation(
            summary = "Create  Members",
            description = "Endpoint used to create members"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created Member",
                            content = @Content(
                                    mediaType = "*/*",
                                    schema = @Schema(
                                            implementation = MemberDTO.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "400",
                                            summary = "Bad request",
                                            description = "Bad request",
                                            value = "malformed request"
                                    )
                            )
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<MemberDTO> save(@Valid @RequestBody MemberDTO memberDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                toMemberDTO(
                        memberService.save(
                                toMember(memberDTO))));
    }

    @Operation(
            summary = "Get all Members",
            description = "Get Members, 10 for page"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Member found",
                            content = @Content(
                                    mediaType = "*/*",
                                    schema = @Schema(
                                            implementation = MemberDTO.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "400",
                                            summary = "Bad request",
                                            description = "Bad request",
                                            value = "Incorrect index"
                                    )
                            )
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<PageResponse<MemberDTO>> findByPage(@Valid @RequestParam("page") int page){
        PageModel<Members> pageMember = memberService.findByPage(page);
        String path = "/members";
        PageResponse response = PageResponse.builder()
                .content(pageMember
                        .getContent()
                        .stream()
                        .map(this::toMemberDTO)
                        .collect(toList()))
                .build();
        response.setResponse(path,page,pageMember.getTotalPages(),pageMember.isFirst(),pageMember.isLast());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Members toMember(MemberDTO memberDTO){
        return Members
                .builder().name(memberDTO.getName())
                .facebookUrl(memberDTO.getFacebookUrl()).instagramUrl(memberDTO.getInstagramUrl())
                .linkedinUrl(memberDTO.getLinkedinUrl()).image(memberDTO.getImage())
                .description(memberDTO.getDescription()).build();
    }

    private MemberDTO toMemberDTO(Members members){
        return MemberDTO.builder()
                .id(members.getId())
                .name(members.getName())
                .facebookUrl(members.getFacebookUrl())
                .instagramUrl(members.getInstagramUrl())
                .linkedinUrl(members.getLinkedinUrl())
                .image(members.getImage())
                .description(members.getDescription())
                .build();
    }

    @Operation(
            summary = "Delete member",
            description = "Endpoint used to delete members"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content, Member deleted",
                    content = @Content(
                            mediaType = "*/*",
                            schema = @Schema(
                                    implementation = MemberDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "400",
                                    summary = "Bad request",
                                    description = "Bad request",
                                    value = "malformed request"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found, Member not yet registered",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "404",
                                    summary = "Not Found",
                                    description = "Not Found",
                                    value = "Member not yet registered"))
            ),
    }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Update member information",
            description = "Endpoint that is used to update data of already registered members"
    )
    @ApiResponses(value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Member found, updated data",
                            content = @Content(
                                    mediaType = "*/*",
                                    schema = @Schema(
                                            implementation = MemberDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "400",
                                            summary = "Bad request",
                                            description = "Bad request",
                                            value = "malformed request"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found, Member not yet registered",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "404",
                                            summary = "Not Found",
                                            description = "Not Found",
                                            value = "Member not yet registered"))
                    ),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> update(@PathVariable Long id,@RequestBody @Valid MemberDTO memberDTO){
        return ResponseEntity.ok().body(toMemberDTO(memberService.update(id, toMember(memberDTO))));
    }

    @Data
    @Builder
    public static class MemberDTO {
        private Long id;
        @NotNull
        private String name;
        private String facebookUrl;
        private String instagramUrl;
        private String linkedinUrl;
        @NotNull
        private String image;
        private String description;
    }




}
